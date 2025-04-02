<script lang="ts">
    let {headerText = 'eispunkt'} = $props();


</script>

<div>
    <header>{{headerText}}</header>
</div>

<style>
    header{
        background-color: #45CAAC;
        padding: 3%;
        text-align: center;
        color: white;
        font-size: 350%;
        font-weight: bold;
    }
</style>