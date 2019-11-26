<template>
  <div class="card">
    <header class="card-header">
      <ul class="tab-heads">
        <li
          class="tab-head"
          v-for="tab in tabs"
          :key="tab"
          v-bind:class="{
            'tab-head--active': activeTab === tab
          }"
          v-on:click="switchTab(tab);"
        >
          <slot :name="tabHeadSlotName(tab)">{{ tab }} </slot>
        </li>
      </ul>
    </header>
    <main class="card-body">
      <div class="tab-panel"><slot :name="tabPanelSlotName"> </slot></div>
    </main>
  </div>
</template>

<script>

export default {
  props: {
    initialTab: String,
    tabs: Array
  },
  data () {
    return {
      activeTab: this.initialTab
    }
  },
  computed: {
    tabPanelSlotName () {
      return `tab-panel-${this.activeTab}`
    }
  },
  methods: {
    tabHeadSlotName (tabName) {
      return `tab-head-${tabName}`
    },

    switchTab (tabName) {
      this.activeTab = tabName
    }
  }
}
</script>

<style scoped>
.card {
  margin-bottom: 1em;
  margin-left: -1em;
  margin-right: -1em;
}

.card-header {
  background-color: white;
  color: #223344;
  padding: 1em 0.5em 0;
}

.tab-heads {
  margin: 0;
  padding: 0;
  list-style: none;
  margin-left: 0.5em;
}

.tab-head {
    display: inline-block;
    background-color: rgba(34, 119, 204, 0.25);
    font-size: 0.79em;
    padding: 0.5em 0.9em !important;
    margin-right: 0.25em;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    position: relative;
    cursor: pointer;
}

.tab-head--active {
  background-color: rgba(34, 119, 204, 0.1);
  transition: 0.4s;
}

.card-body {
    padding: 1em;
    background-color: rgba(34, 119, 204, 0.1);
}

.card-body h2 {
    margin: 0;
}

.card-body p {
    margin-top: 0.5em;
    margin-bottom: 0;
}

@media only screen and (min-width: 568px) {
  .card {
    margin-left: 0;
    margin-right: 0;
  }
  .tab-head {
    font-size: 0.9em;
    padding: 0.5em 0.9em !important;
    margin-right: 0.5em;
  }
  .card-body {
    border-radius: 4px;
  }
}
</style>
