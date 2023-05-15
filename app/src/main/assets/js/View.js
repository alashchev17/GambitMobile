class View {
  selectors = {
    // Displays & Display Components
    rootError: ".root__error",
    firstDisplay: ".first-display",
    loadingDisplay: ".loading-display",
    loadingSpeed: ".loading-display__speed",
    loadingCurrentSize: ".loading-display__currentsize",
    introDisplay: ".intro-display",
    introDisplayButton: ".intro-display__button",
    authDisplay: ".auth-display",
    authCheckbox: ".checkbox__label",
    authCheckboxOrigin: "#checkbox__input",
    checkboxSpan: "#checkbox__span",
    authButton: ".auth-display__button",
    googleDisplay: ".google-display",
    googleReturnButton: ".google-display__return",
    googleError: ".google-display__error",
    mainDisplay: ".main-display",
    mainNickname: "#nickname",
    mainStatus: ".main-display__status",
    mainContent: ".main-content",
    characterSelectCustom: ".main-display__select-box",
    characterSelectContent: ".main-display__select-content",
    characterSelectList: ".main-display__select-list",
    startGameButton: ".main-display__button",
    notificationDisplay: ".notification-content",
    notificationOpen: ".main-display__notification",
    notificationClose: ".notification-content__link--close",
    notificationRead: "#notificationRead",
    newsContent: ".news-content",
    settingsContent: ".settings-content",
    settingsLogoutButton: ".settings-content__button--logout",
    tabMain: ".tabs__item--main",
    tabSettings: ".tabs__item--settings",
    tabNews: ".tabs__item--news",
  };
  active = "--active";
  hidden = "--hidden";
  dnone = "--dnone";
  error = "--error";
  access = "--access";
  displayName = undefined;
  googleCode = "";
  auth = document.querySelectorAll(".auth-display__input");
  googleInputs = document.querySelectorAll(".google-display__input");
  newsCards = document.querySelectorAll(".main-display__card");
  characterSelectOrigin = document.querySelector("#character-select-origin");
  characterSelectOriginOptions = document.querySelectorAll(".main-display__select-options");
  characterSelectItems = document.querySelectorAll(".main-display__select-item");
  characterSelectItemsArray = Array.prototype.slice.call(this.characterSelectItems);
  characterSelectName = document.querySelectorAll(".main-display__select-name");
  notificationCards = document.querySelectorAll(".notification-card");
  notificationCardsArray = Array.prototype.slice.call(this.notificationCards);
  mainTabs = document.querySelectorAll(".tabs__item");
  mainTabsArray = Array.prototype.slice.call(this.mainTabs);

  constructor() {
    for (let key in this.selectors) {
      let selector = document.querySelector(this.selectors[key]);
      this.selectors[key] = selector;
    }
    let selectors = this.selectors;
    // events
    this.events(selectors);
    this.googleInputsHandler(selectors);
    this.tabsHandler(selectors);
    this.notificationsHandler(selectors);
  }

  set display(d) {
    let selector = this.selectors[`${d}Display`];
    if (this.displayName != undefined && d != this.displayName) {
      this.display = this.displayName;
    }
    selector.classList.toggle(selector.classList[0] + this.hidden);
    setTimeout(() => {
      selector.classList.toggle(selector.classList[0] + this.dnone);
      setTimeout(() => {
        selector.classList.toggle(selector.classList[0] + this.active);
      }, 150);
    }, 300);
    this.displayName = d;
    console.log(selector);
  }

  events(selectors) {
    selectors.introDisplayButton.addEventListener("click", event => {
      event.preventDefault();
      this.display = "auth";
    });
    selectors.authButton.addEventListener("click", event => {
      event.preventDefault();
      let check = selectors.authCheckboxOrigin;
      if (this.auth[0].value.trim() == "" && this.auth[1].value.trim() == "") {
        selectors.authButton.setAttribute("disabled", "disabled");
      } else {
        // сделать логику ошибки
        selectors.authButton.removeAttribute("disabled");
        Launcher.auth(this.auth[0].value, this.auth[1].value, check.checked, 0);
      }
    });
    selectors.googleReturnButton.addEventListener("click", event => {
      event.preventDefault();
      this.display = "auth";
    });
    selectors.notificationClose.addEventListener("click", event => {
      event.preventDefault();
      selectors.notificationDisplay.classList.add(selectors.notificationDisplay.classList[0] + this.hidden);
      setTimeout(() => {
        selectors.notificationDisplay.classList.add(selectors.notificationDisplay.classList[0] + this.dnone);
        setTimeout(() => {
          selectors.notificationDisplay.classList.remove(selectors.notificationDisplay.classList[0] + this.active);
        }, 150);
      }, 300);
    });
    selectors.notificationOpen.addEventListener("click", event => {
      event.preventDefault();
      selectors.notificationDisplay.classList.remove(selectors.notificationDisplay.classList[0] + this.dnone);
      setTimeout(() => {
        selectors.notificationDisplay.classList.remove(selectors.notificationDisplay.classList[0] + this.hidden);
        setTimeout(() => {
          selectors.notificationDisplay.classList.add(selectors.notificationDisplay.classList[0] + this.active);
        }, 150);
      }, 300);
    });
    selectors.notificationRead.addEventListener("click", event => {
      event.preventDefault();
      selectors.notificationRead.classList.remove(selectors.notificationRead.classList[0] + this.active);
      this.notificationCards.forEach(item => item.classList.remove(item.classList[0] + this.active));
      selectors.notificationClose.classList.remove(selectors.notificationClose.classList[0] + this.active);
      selectors.notificationOpen.classList.remove(selectors.notificationOpen.classList[0] + this.active);
    });
    this.googleInputs[5].addEventListener("keyup", () => {
      if (this.googleInputs[5].value !== "") {
        for (let i = 0; i < this.googleInputs.length; i++) {
          this.googleCode += this.googleInputs[i].value;
          this.googleInputs[i].setAttribute("disabled", "disabled");
        }
        let googleCodeNumber = Number(this.googleCode);
        console.warn(googleCodeNumber);
        let check = selectors.authCheckboxOrigin;
        Launcher.auth(this.auth[0].value.trim(), this.auth[1].value.trim(), check.checked, googleCodeNumber);
      }
    });
    this.auth.forEach(item => {
      item.addEventListener("change", event => {
        if (item.value.trim() == "") {
          selectors.authButton.setAttribute("disabled", "disabled");
        } else {
          selectors.authButton.removeAttribute("disabled");
        }
      });
    });
    selectors.settingsLogoutButton.addEventListener("click", event => {
      event.preventDefault();
      console.log("Сессия обнулена");
      Launcher.SessionExit();
    });
  }

  googleInputsHandler(selectors) {
    for (let i = 0; i < this.googleInputs.length; i++) {
      this.googleInputs[i].addEventListener("keyup", event => {
        if (this.googleInputs[i].classList.contains(this.googleInputs[i].classList[0] + this.error)) {
          for (let i = 0; i < this.googleInputs.length; i++) {
            this.googleInputs[i].classList.toggle(this.googleInputs[i].classList[0] + this.error);
          }
          selectors.googleError.classList.remove(selectors.googleError.classList[0] + this.active);
        }
        if (this.googleInputs[i].value !== "" && this.googleInputs[i].value !== " " && !isNaN(this.googleInputs[i].value)) {
          if (this.googleInputs[i] == this.googleInputs[5]) {
            this.googleInputs[i].focus();
          } else {
            this.googleInputs[i + 1].focus();
          }
        } else if (event.code == 46 || event.key == "Backspace") {
          if (this.googleInputs[i] == this.googleInputs[0]) {
            return;
          } else {
            this.googleInputs[i - 1].focus();
          }
        }
        else {
          this.googleInputs[i].value = "";
          this.googleInputs[i].focus();
        }
      });
    }
  }


  tabsHandler(selectors) {
    this.newsCards.forEach(item => {
      item.addEventListener("click", event => {
        event.preventDefault();
        selectors.tabMain.classList.remove(selectors.tabMain.classList[0] + this.active);
        selectors.tabNews.classList.add(selectors.tabNews.classList[0] + this.active);
        selectors.newsContent.classList.add("active");
        selectors.newsContent.classList.remove("dnone");
        // стейты контента других табов
        selectors.mainContent.classList.remove("active");
        selectors.mainContent.classList.add("hidden");
        setTimeout(() => {
          selectors.mainContent.classList.add("dnone");
          // отложенный стейт контента таба
          selectors.newsContent.classList.remove("hidden");
        }, 300);
      });
    });

    this.mainTabsArray.forEach(item => {
      item.addEventListener("click", event => {
        event.preventDefault();
        console.log("event.target = " + event.target.classList[1]);
        if (event.target.classList[1] == selectors.tabMain.classList[1]) {
          if (!selectors.tabMain.classList.contains(selectors.tabMain.classList[0] + this.active)) {
            selectors.tabMain.classList.add(selectors.tabMain.classList[0] + this.active);
            selectors.tabNews.classList.remove(selectors.tabNews.classList[0] + this.active);
            selectors.tabSettings.classList.remove(selectors.tabSettings.classList[0] + this.active);
            if (!selectors.mainContent.classList.contains("active")) {
              // стейты контента таба
              selectors.mainContent.classList.add("active");
              selectors.mainContent.classList.remove("dnone");
              // стейты контента других табов
              selectors.newsContent.classList.remove("active");
              selectors.newsContent.classList.add("hidden");
              selectors.settingsContent.classList.add("hidden");
              setTimeout(() => {
                selectors.newsContent.classList.add("dnone");
                selectors.settingsContent.classList.remove("active");
                selectors.settingsContent.classList.add("dnone");
                // отложенный стейт контента таба
                selectors.mainContent.classList.remove("hidden");
              }, 300);
            }
          }
        } else if (event.target.classList[1] == selectors.tabNews.classList[1]) {
          if (!selectors.tabNews.classList.contains(selectors.tabNews.classList[0] + this.active)) {
            selectors.tabNews.classList.add(selectors.tabNews.classList[0] + this.active);
            selectors.tabMain.classList.remove(selectors.tabMain.classList[0] + this.active);
            selectors.tabSettings.classList.remove(selectors.tabSettings.classList[0] + this.active);
            if (!selectors.newsContent.classList.contains("active")) {
              // стейты контента таба
              selectors.newsContent.classList.add("active");
              selectors.newsContent.classList.remove("dnone");
              // стейты контента других табов
              selectors.mainContent.classList.remove("active");
              selectors.mainContent.classList.add("hidden");
              selectors.settingsContent.classList.add("hidden");
              setTimeout(() => {
                selectors.mainContent.classList.add("dnone");
                selectors.settingsContent.classList.remove("active");
                selectors.settingsContent.classList.add("dnone");
                // отложенный стейт контента таба
                selectors.newsContent.classList.remove("hidden");
              }, 300);
            }
          }
        } else if (event.target.classList[1] == selectors.tabSettings.classList[1]) {
          if (!selectors.tabSettings.classList.contains(selectors.tabSettings.classList[0] + this.active)) {
            selectors.tabSettings.classList.add(selectors.tabSettings.classList[0] + this.active);
            selectors.tabMain.classList.remove(selectors.tabNews.classList[0] + this.active);
            selectors.tabNews.classList.remove(selectors.tabSettings.classList[0] + this.active);
            if (!selectors.settingsContent.classList.contains("active")) {
              // стейты контента таба
              selectors.settingsContent.classList.add("active");
              selectors.settingsContent.classList.remove("dnone");
              // стейты контента других табов
              selectors.mainContent.classList.remove("active");
              selectors.mainContent.classList.add("hidden");
              selectors.newsContent.classList.add("hidden");
              setTimeout(() => {
                selectors.mainContent.classList.add("dnone");
                selectors.newsContent.classList.remove("active");
                selectors.newsContent.classList.add("dnone");
                // отложенный стейт контента таба
                selectors.settingsContent.classList.remove("hidden");
              }, 300);
            }
          } 
        }
      });
    });
  }
  checkboxHandler(selectors) {
    selectors.authCheckbox.addEventListener("click", () => {
      if (selectors.authCheckbox.classList.contains("checkbox__label--active")) {
        selectors.authCheckbox.classList.remove("checkbox__label--active");
        selectors.authCheckboxOrigin.checked = false;
      } else {
        selectors.authCheckbox.classList.add("checkbox__label--active");
        selectors.authCheckboxOrigin.checked = true;
      }
      console.log(selectors.authCheckboxOrigin.checked);
    });
  }
  notificationsHandler(selectors) {
    if (this.notificationCardsArray.some(item => item.classList.contains(item.classList[0] + this.active))) {
      selectors.notificationOpen.classList.add(selectors.notificationOpen.classList[0] + this.active);
      selectors.notificationRead.classList.add(selectors.notificationRead.classList[0] + this.active);
    }
    this.notificationCardsArray.forEach(item => {
      item.addEventListener("click", event => {
        event.preventDefault();
        item.classList.remove(item.classList[0] + this.active);
        if (!this.notificationCardsArray.some(item => item.classList.contains(item.classList[0] + this.active))) {
          selectors.notificationOpen.classList.remove(selectors.notificationOpen.classList[0] + this.active);
          selectors.notificationRead.classList.remove(selectors.notificationRead.classList[0] + this.active);
        }
      });
    });
  }
}