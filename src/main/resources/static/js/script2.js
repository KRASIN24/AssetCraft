

const PHOTOS = document.querySelectorAll(".jobs")
const POPUP = document.querySelector(".popup")
const POPUP_CLOSE = document.querySelector(".popup__close")
const POPUP_IMG = document.querySelector(".popup__img")
const ARROW_LEFT = document.querySelector(".popup__arrow--left")
const ARROW_RIGHT = document.querySelector(".popup__arrow--right")
const LENGTH = document.querySelectorAll(".photo")
const BODY = document.getElementById('body')
let Last=[]
let First=[]
let currentImgIndex;

for(i=0;i<LENGTH.length;i++){
  if(i===0){
    Last.push(LENGTH[i].children.length)
  }
  else{
    Last.push(LENGTH[i].children.length+Last[i-1])
  }

}
First.push(0)
for(i=1;i<LENGTH.length;i++)
{
  First.push(Last[i-1])
}

const showNextImg = () => {
  currentImgIndex++;

  for(i=0;i<Last.length;i++)
  {
  if(currentImgIndex===Last[i]-1){
    ARROW_RIGHT.style.display="none"
    ARROW_LEFT.style.display="block"
    break
  }
  else
  {
     ARROW_RIGHT.style.display="block"
  }
}

for(i=0;i<First.length;i++)
  {
  if(currentImgIndex===First[i]){
    ARROW_LEFT.style.display="none"
    ARROW_RIGHT.style.display="block"
    break
  }
  else
  {
     ARROW_LEFT.style.display="block"
  }
}

    POPUP_IMG.src = PHOTOS[currentImgIndex].src;
};

const showPreviousImg = () => {
 
  currentImgIndex--;
  
  for(i=0;i<Last.length;i++)
  {
  if(currentImgIndex===Last[i]-1){
    ARROW_RIGHT.style.display="none"
    ARROW_LEFT.style.display="block"
    break
  }
  else
  {
     ARROW_RIGHT.style.display="block"
  }
}

  for(i=0;i<First.length;i++)
  {
  if(currentImgIndex===First[i]){
    ARROW_LEFT.style.display="none"
    ARROW_RIGHT.style.display="block"
    break
  }
  else
  {
     ARROW_LEFT.style.display="block"
  }
}
  
    POPUP_IMG.src = PHOTOS[currentImgIndex].src;
};

const closePopup = () => {
    POPUP.classList.add("fade-out");
    BODY.style.overflowY = "auto"
    setTimeout(() => {
        POPUP.classList.add("hidden");
        POPUP.classList.remove("fade-out");
        PHOTOS.forEach((element) => {
            element.setAttribute("tabindex", 1);
        });
    }, 300);
};

PHOTOS.forEach((PHOTO, index) => {
    const showPopup = (e) => {
        POPUP.classList.remove("hidden");
        BODY.style.overflowY = "hidden"
        POPUP_IMG.src = e.target.src;
        currentImgIndex = index;
         
        for(i=0;i<Last.length;i++)
          {
          if(currentImgIndex===Last[i]-1){
            ARROW_RIGHT.style.display="none"
            break
          }
          else
          {
             ARROW_RIGHT.style.display="block"
          }
        }

        for(i=0;i<First.length;i++)
  {
  if(currentImgIndex===First[i]){
    ARROW_LEFT.style.display="none"
    break
  }
  else
  {
     ARROW_LEFT.style.display="block"
  }
}

        PHOTOS.forEach((element) => {
            element.setAttribute("tabindex", -1);
        });
    };

    PHOTO.addEventListener("click", showPopup);

    PHOTO.addEventListener("keydown", (e) => {
        if (e.code === "Enter" || e.keyCode === 13) {
            showPopup(e);
        }
    });
});

POPUP_CLOSE.addEventListener("click", closePopup);

ARROW_RIGHT.addEventListener("click", showNextImg);

ARROW_LEFT.addEventListener("click", showPreviousImg);

document.addEventListener("keydown", (e) => {
    if (!POPUP.classList.contains("hidden")) {
        if (e.code === "ArrowRight" || e.keyCode === 39) {
          if(ARROW_RIGHT.style.display!="none")  
          showNextImg();
        }

        if (e.code === "ArrowLeft" || e.keyCode === 37) {
          if(ARROW_LEFT.style.display!="none")
            showPreviousImg();
        }

        if (e.code === "Escape" || e.keyCode === 27) {
            closePopup();
        }
    }
});

POPUP.addEventListener("click", (e) => {
    if (e.target === POPUP) {
        closePopup();
    }
});






function addProduct(productId){
  fetch('/cart/add-api', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
          'X-CSRF-TOKEN': document.getElementById('_csrf').value
      },
      body: JSON.stringify({productId : productId})
  }).then(response => {
      if(response.ok){
          window.location.reload();
      }else{
          alert('Failed');
          alert(response.status);
      }
  }).catch(error => {
      console.error('ERROR: ',error);
  });
}

function editProduct(productId){
  fetch('/cart/add-api', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
          'X-CSRF-TOKEN': document.getElementById('_csrf').value
      },
      body: JSON.stringify({productId : productId})
  }).then(response => {
      if(response.ok){
          window.location.reload();
      }else{
          alert('Failed');
          alert(response.status);
      }
  }).catch(error => {
      console.error('ERROR: ',error);
  });
}