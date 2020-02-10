<script>
import QuestionBase from '@/components/QuestionBase.vue'
export default {
  extends: QuestionBase,
  inheritAttrs: false,
  props: [
  ]
}
</script>
<style>
.items > div {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  display: inline-block;
}

.items {
  margin: 0.6em -0.4em;
}

.items div {
  padding: 0.4em 0;
}

.items input[type="radio"], .items input[type="checkbox"] {
  display: none;
}

.items label {
  background-color: rgba(34,119,204,0.25);
  padding: 0.75em 1em;
  color: #2277CC;
  border: 1px solid #2277CC;
  border-radius: 0.2em;
  margin: 0 0.4em !important;
  display: inline-block;
  cursor: pointer;
  user-select: none;
}

.items label:hover {
  background-color: rgba(34,119,204,0.1);
  transition: background 0.5s ease;
}

.items input[type="radio"]:checked + label, .items input[type="checkbox"]:checked + label {
  background-color: #fff;
}

.items label:before {
  width: 1em;
  height: 1em;
  top: 0.15em;
  display: inline-block;
  content: '';
  border: 1px solid #2277CC;
  background-color: #fff;
  border-radius: 50%;
  position: relative;
  transition: all 0.5s ease;
  margin-right: 1em;
  left: 0px;
}
</style>
