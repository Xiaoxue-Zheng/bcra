<template>
  <div class="components content">
    <!-- Section title with progress badges -->
    <h1>UI components<div class="badge">3</div><div class="badge current">2</div><div class="badge complete"><div>1</div></div></h1>
    <!-- Section introduction paragraph -->
    <p class="introduction">
      Introduction lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi non urna orci. Integer rutrum a leo et eleifend. Nam at lorem sollicitudin, vulputate neque id, commodo ipsum.
    </p>

        <!-- Form start -->

        <form class="pure-form">

          <!-- Radio buttons -->

          <fieldset>
            <label>Radio buttons</label>
            <div class="radio-buttons">
              <div>
                <input type="radio" id="radio1" name="radio" value="">
                <label for="radio1">Radio button 1</label>
              </div>
              <div>
                <input type="radio" id="radio2" name="radio" value="">
                <label for="radio2">Radio button 2</label>
              </div>
              <div>
                <input type="radio" id="radio3" name="radio" value="">
                <label for="radio3">Radio button 3</label>
              </div>
            </div>
          </fieldset>

          <!-- Check buttons -->

          <fieldset>
            <label>Check buttons</label>
            <div class="check-buttons">
              <div>
                <input type="checkbox" id="checkbutton1" name="radio" value="">
                <label for="checkbutton1">Check button 1</label>
              </div>
              <div>
                <input type="checkbox" id="checkbutton2" name="radio" value="">
                <label for="checkbutton2">Check button 2</label>
              </div>
              <div>
                <input type="checkbox" id="checkbutton3" name="radio" value="">
                <label for="checkbutton3">Check button 3</label>
              </div>
            </div>
          </fieldset>

          <!-- Checkboxes for consent page -->

          <br>
          <h1>Checkboxes</h1>
          <p class="instructions">
            Instructions lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi non urna orci. Integer rutrum a leo et eleifend. Nam at lorem sollicitudin, vulputate neque id, commodo ipsum.
          </p>
          <fieldset>
            <div class="checkboxes">
              <div class="pure-u-1">
                <input type="checkbox" id="checkbox1" name="" value="">
                <label for="checkbox1">I confirm that I have read the information sheet for this study. I have had the opportunity to consider the information, ask questions and have had these answered satisfactorily.</label>
              </div>
              <div class="pure-u-1">
                <input type="checkbox" id="checkbox2" name="" value="">
                <label for="checkbox2">I understand that my participation is voluntary and that I am free to withdraw at any time without giving any reason, without my medical care or legal rights being affected.</label>
              </div>
              <div class="pure-u-1">
                <input type="checkbox" id="checkbox3" name="" value="">
                <label for="checkbox3">I agree to take part in the above study.</label>
              </div>
            </div>
          </fieldset>

          <!-- Input group -->
          <fieldset>
            <label>Input group</label>
            <div class="input-group">
              <div class="input-group-area">
                <input required v-model="age" type="text" class="pure-input-1"/>
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
                <input required v-model="height" type="text" />
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

          <!-- Buttons -->

          <button class="pure-button pure-button-primary" type="submit">Primary button</button>
          <button class="pure-button pure-button-secondary" type="submit">Secondary button</button>
        </form>

        <!-- Form end -->

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
