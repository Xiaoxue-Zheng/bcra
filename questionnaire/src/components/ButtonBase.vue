<script>
export default {
  props: [
    'to',
    'clickEvent',
    'readOnly'
  ]
}
</script>
<style>
.pure-button {
    display: inline-block;
    border-radius: 0.2em;
    margin: 2em 0.75em 2em 0;
    padding: 0.75em 1em;
    border: 2px solid rgb(34, 119, 204);
}

@media only screen and (max-width: 568px) {
.pure-button {
        width: 100%;;
    }
}

.pure-button-disabled {
    background-color: rgba(34, 51, 68, 0.25);
    color:  rgba(34, 51, 68, 0.75);
}
</style>
