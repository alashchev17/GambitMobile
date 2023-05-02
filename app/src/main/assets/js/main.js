// Initializing Displays
const firstDisplay = document.querySelector(".first-display");
const loadingDisplay = document.querySelector(".loading-display");
const introDisplay = document.querySelector(".intro-display");
const authDisplay = document.querySelector(".auth-display");
const googleDisplay = document.querySelector(".google-display");
const mainDisplay = document.querySelector(".main-display");
const mainContent = document.querySelector(".main-content");
const newsContent = document.querySelector(".news-content");
const settingsContent = document.querySelector(".settings-content");

// Initializing Display Components
const introDisplayButton = document.querySelector(".intro-display__button");
const authCheckbox = document.querySelector(".checkbox__label");
const authCheckboxOrigin = document.querySelector("#checkbox__input");
const checkboxSpan = document.querySelector("#checkbox__span");
const authButton = document.querySelector(".auth-display__button");
const googleReturnButton = document.querySelector(".google-display__return");
const googleInputs = document.querySelectorAll(".google-display__input");
const googleError = document.querySelector(".google-display__error");
const tabMain = document.querySelector(".tabs__item--main");
const tabNews = document.querySelector(".tabs__item--news");
const tabSettings = document.querySelector(".tabs__item--settings");
const mainStatus = document.querySelector(".main-display__status");
const newsCards = document.querySelectorAll(".main-display__card");
const characterSelectOrigin = document.querySelector("#character-select-origin");
const characterSelectOriginOptions = document.querySelectorAll(".main-display__select-options");
const characterSelectCustom = document.querySelector(".main-display__select-box");
const characterSelectContent = document.querySelector(".main-display__select-content");
const characterSelectItems = document.querySelectorAll(".main-display__select-item");
const characterSelectName = document.querySelectorAll(".main-display__select-name");
const mainDisplayButton = document.querySelector(".main-display__button");

// Functions

function showHidePassword(target){
	let input = document.getElementById('password');
	if (input.getAttribute('type') == 'password') {
		target.classList.add('view');
		input.setAttribute('type', 'text');
	} else {
		target.classList.remove('view');
		input.setAttribute('type', 'password');
	}
	return false;
}

function checkReadyStatus() {
  if (mainStatus.classList.contains("main-display__status--access")) {
    mainStatus.innerHTML = "Установлена актуальная версия лаунчера";
  } else if (mainStatus.classList.contains("main-display__status--warning")) {
    mainStatus.innerHTML = "Необходимо обновить клиент";
  } else if (mainStatus.classList.contains("main-display__status--error")) {
    mainStatus.innerHTML = "Произошёл системный сбой!";
  }
}

setTimeout(() => {
  firstDisplay.classList.add("first-display--hidden");
  setTimeout(() => {
    firstDisplay.classList.add("first-display--dnone");
    loadingDisplay.classList.remove("loading-display--dnone");
    setTimeout(() => {
      loadingDisplay.classList.add("loading-display--active");
    }, 150);
    setTimeout(() => {
      loadingDisplay.classList.add("loading-display--hidden");
      setTimeout(() => {
        loadingDisplay.classList.add("loading-display--dnone");
        setTimeout(() => {
          introDisplay.classList.add("intro-display--active");
        }, 150);
        introDisplay.classList.remove("intro-display--dnone");
      }, 290);
    }, 1500);
  }, 290);
}, 1500);

introDisplayButton.addEventListener("click", e => {
  e.preventDefault();
  introDisplay.classList.remove("intro-display--active");
  setTimeout(() => {
    introDisplay.classList.add("intro-display--hidden");
  }, 150)
  setTimeout(() => {
    introDisplay.classList.add("intro-display--dnone");
    setTimeout(() => {
      authDisplay.classList.add("auth-display--active");
    }, 150);
    authDisplay.classList.remove("auth-display--dnone");
  }, 300);
});

authCheckbox.addEventListener("click", () => {
  if (authCheckbox.classList.contains("checkbox__label--active")) {
    authCheckbox.classList.remove("checkbox__label--active");
    authCheckboxOrigin.checked = false;
  } else {
    authCheckbox.classList.add("checkbox__label--active");
    authCheckboxOrigin.checked = true;
  }
  console.log(authCheckboxOrigin.checked);
});

checkboxSpan.addEventListener("click", () => {
  if (!authCheckbox.classList.contains("checkbox__label--active")) {
    authCheckboxOrigin.checked = true;
  } else {
    authCheckboxOrigin.checked = false;
  }
  authCheckbox.classList.toggle("checkbox__label--active");
  console.log(authCheckboxOrigin.checked);
});

const googleInputsArray = Array.prototype.slice.call(googleInputs);

authButton.addEventListener("click", e => {
  e.preventDefault();
  authDisplay.classList.remove("auth-display--active");
  setTimeout(() => {
    authDisplay.classList.add("auth-display--hidden");
  }, 150)
    setTimeout(() => {
      authDisplay.classList.add("auth-display--dnone");
      googleDisplay.classList.remove("google-display--dnone");
      googleDisplay.classList.remove("google-display--hidden");
      googleInputsArray[0].focus();
      setTimeout(() => {
        googleDisplay.classList.add("google-display--active");
      }, 150);
    }, 300);
});

googleReturnButton.addEventListener("click", e => {
  e.preventDefault();
  googleDisplay.classList.add("google-display--hidden");
  googleDisplay.classList.remove("google-display--active");
  setTimeout(() => {
    googleDisplay.classList.add("google-display--dnone");
    authDisplay.classList.add("auth-display--active");
    authDisplay.classList.remove("auth-display--hidden");
    authDisplay.classList.remove("auth-display--dnone");
  }, 300);
});

for (let i = 0; i < googleInputsArray.length; i++) {
  googleInputsArray[i].addEventListener("keyup", event => {
    if (googleInputsArray[i].classList.contains("google-display__input--error")) {
      for (let i = 0; i < googleInputsArray.length; i++) {
        googleInputsArray[i].classList.remove("google-display__input--error");
        googleError.classList.remove("google-display__error--active");
      }
    }
    if (googleInputsArray[i].value !== "" && googleInputsArray[i].value !== " " && !isNaN(googleInputsArray[i].value)) {
      if (googleInputsArray[i] == googleInputsArray[5]) {
        googleInputsArray[i].focus();
      } else {
        googleInputsArray[i + 1].focus();
      }
    } else if (event.code == 46 || event.key == "Backspace") {
      if (googleInputsArray[i] == googleInputsArray[0]) {
        return;
      } else {
        googleInputsArray[i - 1].focus();
      }
    }
    else {
      googleInputsArray[i].value = "";
      googleInputsArray[i].focus();
    }
  });
}
let authentificatorLine = "";
googleInputsArray[5].addEventListener("keyup", event => {
  if (googleInputsArray[5].value !== "") {
    for (let i = 0; i < googleInputsArray.length; i++) {
      authentificatorLine += googleInputsArray[i].value;
    }
    if (authentificatorLine !== "123456") {
      googleError.classList.add("google-display__error--active");
      for (let i = 0; i < googleInputsArray.length; i++) {
        googleInputsArray[i].classList.add("google-display__input--error");
        googleInputsArray[i].value = "";
      }
      authentificatorLine = "";
      googleInputsArray[0].focus();
    } else if (authentificatorLine == "123456") {
      // тестовый подтверждающий код
      googleDisplay.classList.remove("google-display--active");
      setTimeout(() => {
        googleDisplay.classList.add("google-display--hidden");
      }, 150);
      setTimeout(() => {
        googleDisplay.classList.add("google-display--dnone");
        mainDisplay.classList.remove("main-display--dnone");
        setTimeout(() => {
          mainDisplay.classList.add("main-display--active");
        }, 150);
      }, 300);
    }
  }
});

tabMain.addEventListener("click", e => {
  e.preventDefault();
  tabMain.classList.add("tabs__item--active");
  if (newsContent.classList.contains("active")) {
    newsContent.classList.remove("active");
    newsContent.classList.add("hidden");
    setTimeout(() => {
      newsContent.classList.add("dnone");
      mainContent.classList.remove("dnone");
      mainContent.classList.add("active");
      setTimeout(() => {
        mainContent.classList.remove("hidden");
      }, 100);
    }, 300);
  } else if (settingsContent.classList.contains("active")) {
    settingsContent.classList.remove("active");
    settingsContent.classList.add("hidden");
    setTimeout(() => {
      settingsContent.classList.add("dnone");
      mainContent.classList.remove("dnone");
      mainContent.classList.add("active");
      setTimeout(() => {
        mainContent.classList.remove("hidden");
      }, 100);
    }, 300);
  }
  if (tabNews.classList.contains("tabs__item--active")) {
    tabNews.classList.remove("tabs__item--active");
  } else if (tabSettings.classList.contains("tabs__item--active")) {
    tabSettings.classList.remove("tabs__item--active");
  }
})
tabNews.addEventListener("click", e => {
  e.preventDefault();
  tabNews.classList.add("tabs__item--active");
  if (mainContent.classList.contains("active")) {
    mainContent.classList.remove("active");
    mainContent.classList.add("hidden");
    setTimeout(() => {
      mainContent.classList.add("dnone");
      newsContent.classList.remove("dnone");
      newsContent.classList.add("active");
      setTimeout(() => {
        newsContent.classList.remove("hidden");
      }, 100);
    }, 300);
  } else if (settingsContent.classList.contains("active")) {
    settingsContent.classList.remove("active");
    settingsContent.classList.add("hidden");
    setTimeout(() => {
      settingsContent.classList.add("dnone");
      newsContent.classList.remove("dnone");
      newsContent.classList.add("active");
      setTimeout(() => {
        newsContent.classList.remove("hidden");
      }, 100);
    }, 300);
  }
  if (tabMain.classList.contains("tabs__item--active")) {
    tabMain.classList.remove("tabs__item--active");
  } else if (tabSettings.classList.contains("tabs__item--active")) {
    tabSettings.classList.remove("tabs__item--active");
  }
})
tabSettings.addEventListener("click", e => {
  e.preventDefault();
  tabSettings.classList.add("tabs__item--active");
  if (mainContent.classList.contains("active")) {
    mainContent.classList.remove("active");
    mainContent.classList.add("hidden");
    setTimeout(() => {
      mainContent.classList.add("dnone");
      settingsContent.classList.remove("dnone");
      settingsContent.classList.add("active");
      setTimeout(() => {
        settingsContent.classList.remove("hidden");
      }, 100);
    }, 300);
  } else if (newsContent.classList.contains("active")) {
    newsContent.classList.remove("active");
    newsContent.classList.add("hidden");
    setTimeout(() => {
      newsContent.classList.add("dnone");
      settingsContent.classList.remove("dnone");
      settingsContent.classList.add("active");
      setTimeout(() => {
        settingsContent.classList.remove("hidden");
      }, 100);
    }, 300);
  }
  if (tabMain.classList.contains("tabs__item--active")) {
    tabMain.classList.remove("tabs__item--active");
  } else if (tabNews.classList.contains("tabs__item--active")) {
    tabNews.classList.remove("tabs__item--active");
  }
});

newsCards.forEach(item => {
  item.addEventListener("click", e => {
    e.preventDefault();
    mainContent.classList.remove("active");
    mainContent.classList.add("hidden");
    tabMain.classList.remove("tabs__item--active");
    tabNews.classList.add("tabs__item--active");
    setTimeout(() => {
      mainContent.classList.add("dnone");
      newsContent.classList.remove("dnone");
      newsContent.classList.add("active");
      setTimeout(() => {
        newsContent.classList.remove("hidden");
      }, 100);
    }, 300);
  });
});

const characterSelectItemsArray = Array.prototype.slice.call(characterSelectItems);

characterSelectCustom.addEventListener("click", e => {
  e.preventDefault();
  if (characterSelectItemsArray.some(item => item.classList.contains("active"))) {
    characterSelectItems.forEach(item => {
      item.classList.remove("active");
    });
    characterSelectOriginOptions[0].setAttribute("selected", "selected");
  }
  setTimeout(() => {
    characterSelectCustom.classList.toggle("active");
    if (characterSelectCustom.classList.contains("active")) {
      mainDisplayButton.classList.add("hidden");
      mainDisplayButton.classList.remove("active");
      setTimeout(() => {
        setTimeout(() => {
          characterSelectContent.classList.toggle("dnone");
        }, 150)
        characterSelectContent.classList.toggle("active");
        characterSelectContent.classList.toggle("hidden");
      }, 50);
    } else {
      characterSelectContent.classList.toggle("active");
      characterSelectContent.classList.toggle("hidden");
      setTimeout(() => {
        mainDisplayButton.classList.add("active");
        mainDisplayButton.classList.remove("hidden");
        characterSelectContent.classList.toggle("dnone");
      }, 300);
    }
  }, 150);
});

characterSelectItems.forEach((item, index) => {
  item.addEventListener("click", () => {
    characterSelectOriginOptions[index].removeAttribute("selected");
    characterSelectOriginOptions[index + 1].setAttribute("selected", "selected");
    if (characterSelectOriginOptions[index + 1].hasAttribute("selected")) {
      characterSelectOriginOptions[index + 1].removeAttribute("selected");
      characterSelectOriginOptions[index + 1].setAttribute("selected", "selected");
    }
    item.classList.toggle("active");
    characterSelectCustom.textContent = characterSelectName[index].textContent;
    // прячем кастомный селект
    setTimeout(() => {
      characterSelectCustom.classList.remove("active");
      characterSelectContent.classList.remove("active");
      characterSelectContent.classList.add("hidden");
      setTimeout(() => {
        characterSelectContent.classList.add("dnone");
        setTimeout(() => {
          mainDisplayButton.classList.add("active");
          mainDisplayButton.classList.remove("hidden");
        }, 150);
      }, 150);
    }, 100);
  });
});