function controlFromInput(fromSlider, fromInput, toInput, controlSlider) {
    const [from, to] = getParsed(fromInput, toInput);
    fillSlider(fromInput, toInput, outsideColor, insideColor, controlSlider);
    if (from > to) {
        fromSlider.value = to;
        fromInput.value = to;
    } else {
        fromSlider.value = from;
    }
}
    
function controlToInput(toSlider, fromInput, toInput, controlSlider) {
    const [from, to] = getParsed(fromInput, toInput);
    fillSlider(fromInput, toInput, outsideColor, insideColor, controlSlider);
    setToggleAccessible(toInput);
    if (from <= to) {
        toSlider.value = to;
        toInput.value = to;
    } else {
        toInput.value = from;
    }
}

function controlFromSlider(fromSlider, toSlider, fromInput) {
  const [from, to] = getParsed(fromSlider, toSlider);
  fillSlider(fromSlider, toSlider, outsideColor, insideColor, toSlider);
  if (from > to) {
    fromSlider.value = to;
    fromInput.value = to;
  } else {
    fromInput.value = from;
  }
}

function controlToSlider(fromSlider, toSlider, toInput) {
  const [from, to] = getParsed(fromSlider, toSlider);
  fillSlider(fromSlider, toSlider, outsideColor, insideColor, toSlider);
  setToggleAccessible(toSlider);
  if (from <= to) {
    toSlider.value = to;
    toInput.value = to;
  } else {
    toInput.value = from;
    toSlider.value = from;
  }
}

function getParsed(currentFrom, currentTo) {
  const from = parseInt(currentFrom.value, 10);
  const to = parseInt(currentTo.value, 10);
  return [from, to];
}

function fillSlider(from, to, sliderColor, rangeColor, controlSlider) {
    const rangeDistance = to.max-to.min;
    const fromPosition = from.value - to.min;
    const toPosition = to.value - to.min;
    controlSlider.style.background = `linear-gradient(
      to right,
      ${sliderColor} 0%,
      ${sliderColor} ${(fromPosition)/(rangeDistance)*100}%,
      ${rangeColor} ${((fromPosition)/(rangeDistance))*100}%,
      ${rangeColor} ${(toPosition)/(rangeDistance)*100}%, 
      ${sliderColor} ${(toPosition)/(rangeDistance)*100}%, 
      ${sliderColor} 100%)`;
}

function setToggleAccessible(currentTarget) {
  const toSlider = document.querySelector('#toSlider');
  if (Number(currentTarget.value) <= 0 ) {
    toSlider.style.zIndex = 2;
  } else {
    toSlider.style.zIndex = 0;
  }
}

const fromSlider = document.querySelector('#fromSlider');
const toSlider = document.querySelector('#toSlider');
const fromInput = document.querySelector('#fromInput');
const toInput = document.querySelector('#toInput');

const outsideColor = '#C6C6C6';
const insideColor = '#4e49a5';
fillSlider(fromSlider, toSlider, outsideColor, insideColor, toSlider);
setToggleAccessible(toSlider);


fromSlider.oninput = () => controlFromSlider(fromSlider, toSlider, fromInput);
toSlider.oninput = () => controlToSlider(fromSlider, toSlider, toInput);
fromInput.oninput = () => controlFromInput(fromSlider, fromInput, toInput, toSlider);
toInput.oninput = () => controlToInput(toSlider, fromInput, toInput, toSlider);

function resetCost() {
    const smallestPrice = document.getElementById('fromInput').min;
    const biggestPrice = document.getElementById('toInput').max;

    document.getElementById('fromInput').value = "";
    document.getElementById('toInput').value = "";

    document.getElementById('fromInput').placeholder = smallestPrice;
    document.getElementById('toInput').placeholder = biggestPrice;

    // Reset the range sliders
    document.getElementById('fromSlider').value = smallestPrice;
    document.getElementById('toSlider').value = biggestPrice;

    fillSlider(fromSlider, toSlider, outsideColor, insideColor, toSlider);
}

function resetRating() {
  const rangeInput = document.getElementById('ratingRange');
  rangeInput.value = 0; // Reset to default value or any value you prefer
  rangeInput.style.setProperty('--value', rangeInput.value); // Update the CSS variable if needed
}

function resetCategories() {
  const checkboxes = document.querySelectorAll('.checkbox');
      checkboxes.forEach(function(checkbox) {
        checkbox.checked = false;
      });
    }

 document.getElementById('filterForm').addEventListener('submit',function(event){
    const ratingInput = document.getElementById('ratingRange');
    const fromInput = document.querySelector('#fromInput');
    const toInput = document.querySelector('#toInput');
    const fromSlider = document.getElementById('fromSlider');
    const toSlider = document.getElementById('toSlider');
    const searchBar = document.getElementById('hiddenSearch');

    if (ratingInput.value === '0')
      ratingInput.name = '';

    if (fromInput.value === fromInput.min)
      fromSlider.name = '';

    if (toInput.value === toInput)
      toSlider.name = '';

    if(searchBar.value === '')
      searchBar.name = '';
 });

 
 function resetFilter(){
  // FIXME: resetCategories() work on their own but not in this function
    resetCategories();  
    resetRating();
    resetCost();
 }
