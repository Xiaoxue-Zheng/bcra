<template>
  <div class="components content">
    <!-- Section title with progress badges -->
    <h1>UI components</h1>

        <!-- Form start -->
        <form class="pure-form">

          <!-- Input group -->
          <fieldset>
            <label>Input group</label>
            <div class="input-group">
              <div class="input-group-area">
                <input required type="text" class="pure-input-1"/><!--v-model="age"-->
              </div>
              <div class="input-group-unit">
                Years
              </div>
            </div>
          </fieldset>

          <!-- Input group select -->
          <fieldset>
            <label>Input group select</label>
            <div class="input-group-select">
                <div>
                <input required type="text" /><!--v-model="height"-->
                <select id="height" class="select-css">
                    <option>Metres</option>
                    <option>Feet</option>
                </select>
                </div>
            </div>
          </fieldset>

           <!-- Select -->
          <fieldset>
            <label>Select</label>
              <div>
                <select id="number" class="select-css">
                    <option>None</option>
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                </select>
              </div>
          </fieldset>

        <!-- Form end -->
        </form>

    <!-- Yellow info box -->
    <div class="info-box">
      <h3>Yellow info box</h3>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sit amet justo vitae justo faucibus sagittis. Vestibulum luctus risus lorem, in congue arcu placerat eget. Nulla quis auctor dui.</p>
      <ul>
        <li>List item 1</li>
        <li>List item 2</li>
        <li>List item 3</li>
      </ul>
      <p>Duis lacus risus, tincidunt vitae nunc id, euismod tempor odio. Etiam mollis dictum imperdiet. Phasellus sollicitudin ante non gravida ultrices.</p>
    </div>
  </div>
</template>
