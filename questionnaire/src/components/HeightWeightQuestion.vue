<template>
  <fieldset>
    <div class="pure-u-1">
      <label>{{ question.text }}</label>
      <div>
        <div class="input-group-select">
          <input
            required
            type="text"
            v-model="bigValue"
          />
          <select
            class="select-css"
            v-model="units"
          >
            <option v-if="question.type==='NUMBER_HEIGHT'" value="CENTIMETERS">Centimeters</option>
            <option v-if="question.type==='NUMBER_HEIGHT'" value="INCHES">Feet</option>
            <option v-if="question.type==='NUMBER_WEIGHT'" value="KILOS">Kilos</option>
            <option v-if="question.type==='NUMBER_WEIGHT'" value="POUNDS">Stones</option>
          </select>
        </div>
        <div
          class="input-group"
          v-if="this.answer.units === this.IMPERIAL_UNITS"
        >
          <div class="input-group-area">
            <input
              type="number"
              class="pure-input-1"
              v-model="smallValue"
              min="0"
              :max="(question.type==='NUMBER_HEIGHT') ? 11 : 13"
              step="1"
            /></div>
          <div v-if="question.type==='NUMBER_HEIGHT'" class="input-group-unit">Inches</div>
          <div v-if="question.type==='NUMBER_WEIGHT'" class="input-group-unit">Pounds</div>
        </div>
      </div>
    </div>
  </fieldset>
</template>
<script>
import QuestionBase from '@/components/QuestionBase.vue'
const INCHES_PER_FOOT = 12
const POUNDS_PER_STONE = 14

export default {
  extends: QuestionBase,
  inheritAttrs: false,
  data () {
    return {
      bigNumber: 0,
      smallNumber: 0,
      METRIC_UNITS: (this.question.type === 'NUMBER_HEIGHT') ? 'CENTIMETERS' : 'KILOS',
      IMPERIAL_UNITS: (this.question.type === 'NUMBER_HEIGHT') ? 'INCHES' : 'POUNDS',
      SCALING_FACTOR: (this.question.type === 'NUMBER_HEIGHT') ? INCHES_PER_FOOT : POUNDS_PER_STONE
    }
  },
  computed: {
    units: {
      get () {
        return this.answer.units
      },
      set (units) {
        this.bigNumber = 0
        this.smallNumber = 0
        this.answer.number = null
        this.answer.units = units
      }
    },
    bigValue: {
      get () {
        if (this.answer.number === null) return ''
        return (
          (this.answer.units === this.METRIC_UNITS)
            ? this.answer.number
            : Math.floor(this.answer.number / this.SCALING_FACTOR)
        )
      },
      set (value) {
        if (value === '') value = 0
        this.bigNumber = value
        this.setAnswerNumber()
      }
    },
    smallValue: {
      get () {
        if (this.answer.number === null) return ''
        return (
          (this.answer.units === this.METRIC_UNITS)
            ? 0
            : this.answer.number % this.SCALING_FACTOR
        )
      },
      set (value) {
        if (value === '') value = 0
        this.smallNumber = value
        this.setAnswerNumber()
      }
    }
  },
  methods: {
    setAnswerNumber () {
      var number =
        (this.answer.units === this.METRIC_UNITS)
          ? this.bigNumber
          : parseInt(this.bigNumber * this.SCALING_FACTOR) + parseInt(this.smallNumber)
      if (number === 0) {
        number = null
      }
      this.answer.number = number
    }
  }
}
</script>
<style scoped>
.input-group-select {
  margin-top: 1em;
  display: inline-block;
}
.input-group-select input {
  border-radius: 0.2em 0 0 0.2em !important;
  width: 6em;
}

.input-group-select select {
  border-radius: 0 0.2em 0.2em 0 !important;
  border-left: 0px !important;
  height: 2.31em !important;
}

.input-group {
  display: inline-table;
  margin: 1em 0 1em 2em;
}

.input-group-unit {
  display: table-cell;
  vertical-align: middle;
  background: rgba(34, 51, 68, 0.1);
  border-radius: 0em 0.2em 0.2em 0em !important;
  padding: 0em 0.75em;
  border: 1px solid #ccc;
  border-left: 0px;
}

.input-group input {
  display: block;
  width: 100%;
  border-radius: 0.2em 0em 0em 0.2em !important;
}
</style>
