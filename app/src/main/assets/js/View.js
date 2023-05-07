class View {
  selectors = {
    // Displays & Display Components
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
    mainStatus: ".main-display__status",
    mainContent: ".main-content",
    characterSelectOrigin: "#character-select-origin",
    characterSelectCustom: ".main-display__select-box",
    characterSelectContent: ".main-display__select-content",
    notificationDisplay: ".notification-content",
    notificationOpen: ".main-display__notification",
    notificationClose: ".notification-content__link--close",
    notificationRead: "#notificationRead",
    mainDisplayButton: ".main-display__button",
    newsContent: ".news-content",
    settingsContent: ".settings-content",
    tabMain: ".tabs__item--main",
    tabSettings: ".tabs__item--settings",
    tabNews: ".tabs__item--news",
  };
  active = "--active";
  hidden = "--hidden";
  dnone = "--dnone";
  error = "--error";
  access = "--access"
  displayName = undefined;
  googleCode = "";
  googleInputs = document.querySelectorAll(".google-display__input");
  newsCards = document.querySelectorAll(".main-display__card");
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
    this.mainSelectHandler(selectors);
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
      this.display = "google";
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
  }

  googleInputsHandler(selectors) {
    for (let i = 0; i < this.googleInputs.length; i++) {
      this.googleInputs[i].addEventListener("keyup", event => {
        if (this.googleInputs[i].classList.contains(this.googleInputs[i].classList[0] + this.error)) {
          for (let i = 0; i < this.googleInputs.length; i++) {
            this.googleInputs[i].classList.toggle(this.googleInputs[i].classList[0] + this.error);
            selectors.googleError.classList.remove(selectors.googleError.classList[0] + this.active);
          }
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

  mainSelectHandler(selectors) {
    selectors.characterSelectCustom.addEventListener("click", event => {
      event.preventDefault();
      if (this.characterSelectItemsArray.some(item => item.classList.contains("active"))) {
        this.characterSelectItems.forEach(item => {
          item.classList.remove("active");
        });
        this.characterSelectOriginOptions[0].setAttribute("selected", "selected");
      }
      setTimeout(() => {
        selectors.characterSelectCustom.classList.toggle("active");
        if (selectors.characterSelectCustom.classList.contains("active")) {
          selectors.mainDisplayButton.classList.add("hidden");
          selectors.mainDisplayButton.classList.remove("active");
          setTimeout(() => {
            setTimeout(() => {
              selectors.characterSelectContent.classList.toggle("dnone");
            }, 150)
            selectors.characterSelectContent.classList.toggle("active");
            selectors.characterSelectContent.classList.toggle("hidden");
          }, 50);
        } else {
          selectors.characterSelectContent.classList.toggle("active");
          selectors.characterSelectContent.classList.toggle("hidden");
          setTimeout(() => {
            selectors.mainDisplayButton.classList.add("active");
            selectors.mainDisplayButton.classList.remove("hidden");
            selectors.characterSelectContent.classList.toggle("dnone");
          }, 300);
        }
      }, 150);
    });

    this.characterSelectItems.forEach((item, index) => {
      item.addEventListener("click", () => {
        this.characterSelectOriginOptions[index].removeAttribute("selected");
        this.characterSelectOriginOptions[index + 1].setAttribute("selected", "selected");
        if (this.characterSelectOriginOptions[index + 1].hasAttribute("selected")) {
          this.characterSelectOriginOptions[index + 1].removeAttribute("selected");
          this.characterSelectOriginOptions[index + 1].setAttribute("selected", "selected");
        }
        item.classList.toggle("active");
        console.log(selectors.characterSelectOrigin.value);
        selectors.characterSelectCustom.textContent = this.characterSelectName[index].textContent;
        // прячем кастомный селект
        setTimeout(() => {
          selectors.characterSelectCustom.classList.remove("active");
          selectors.characterSelectContent.classList.remove("active");
          selectors.characterSelectContent.classList.add("hidden");
          setTimeout(() => {
            selectors.characterSelectContent.classList.add("dnone");
            setTimeout(() => {
              selectors.mainDisplayButton.classList.add("active");
              selectors.mainDisplayButton.classList.remove("hidden");
            }, 150);
          }, 150);
        }, 750);
      });
    });
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
        console.log("ивент запущен, event.target = " + event.target.classList[1]);
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
  }
}