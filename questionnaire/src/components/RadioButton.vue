<template>
    <div>
        <input type="radio" id="radio" name="radio" value="">
        <label for="radio"><slot name="text"></slot></label>
    </div>
</template>
<script>
export default {
  name: 'RadioButton',
  props: {
    text: String
  }
}
</script>
<style scoped>

    .radio-buttons > div {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    }

    .radio-buttons {
    margin: 0em -0.4em;
    }

    .radio-buttons div {
    padding-right: 0.4em;
    padding-left: 0.4em;
    padding-bottom: 0.4em;
    }

    .radio-buttons input[type="radio"] {
    display: none;
    }

    .radio-buttons label {
    background-color: rgba(34,119,204,0.25);
    padding: 0.75em 1em;
    color: #2277CC;
    border: 1px solid #2277CC;
    border-radius: 0.2em;
    margin: 0.4em !important;
    display: inline-block;
    cursor: pointer;
    }

    .radio-buttons label:first-of-type {
    margin-top: 0 !important;
    }

    .radio-buttons label:hover {
    background-color: rgba(34,119,204,0.1);
    transition: background 0.5s ease;
    }

    .radio-buttons input[type="radio"]:checked + label {
    background-color: #fff;
    border-color: #2277CC;
    }

    .radio-buttons label:before {
    width: 1em;
    height: 1em;
    top: 0.15em;
    display: inline-block;
    content: '';
    border: 1px solid #2277CC;
    background-color: #fff;
    border-radius: 50%;
    z-index: 2;
    position: relative;
    transition: all 0.5s ease;
    margin-right: 1em;
    }

    .radio-buttons input:checked + label:before {
    background-color: #2277CC;
    border-color: #2277CC;
    -moz-box-shadow: inset 0 0 0 0.25em white;
    -webkit-box-shadow: inset 0 0 0 0.25em white;
    box-shadow: inset 0 0 0 0.25em white;
    }

</style>
