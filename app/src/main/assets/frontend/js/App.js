class App extends View {
  #session = null;
  #loadTime = 0;
  #minLoadTime = 3000;
  constructor() {
    super();
    this.displaysInit();
    this.checkboxData(this.selectors);
    console.log("App init");
  }
  displaysInit() {
    if (this.displayName == undefined) {
      this.display = "first";
      this.#loadTime = this.time;
    } else {
      if(this.#loadTime+this.#minLoadTime > this.time) {
        setTimeout(() => {
          this.display = "intro"
        }, this.#loadTime+this.#minLoadTime-this.time);
      } else {
        this.display = "intro";
      }
    }
  }
  timeConverter(UNIX_timestamp) {
      let a = new Date(UNIX_timestamp * 1000);
      let months = ['Янв','Фев','Мар','Апр','Май','Июн','Июл','Авг','Сен','Окт','Ноя','Дек'];
      let year = a.getFullYear();
      let month = months[a.getMonth()];
      let date = a.getDate();
      let hour = a.getHours();
      let min = a.getMinutes();
      let sec = a.getSeconds();
      // формируем строку со временем по формату "00 мес 0000 00:00:00"
      let time = `${date} ${month} ${year} `;
      time += ((hour < 10) ? "0" : "") + hour;
      time += ((min < 10) ? ":0" : ":") + min;
      time += ((sec < 10) ? ":0" : ":") + sec;
      return time;
    }
  checkboxData(selectors) {
    selectors.checkboxSpan.addEventListener("click", () => {
      if (!selectors.authCheckbox.classList.contains(selectors.authCheckbox.classList[0] + this.active)) {
        selectors.authCheckboxOrigin.checked = true;
      } else {
        selectors.authCheckboxOrigin.checked = false;
      }
      selectors.authCheckbox.classList.toggle(selectors.authCheckbox.classList[0] + this.active);
      console.log(selectors.authCheckboxOrigin.checked);
    });
    selectors.authCheckbox.addEventListener("click", () => {
      if (!selectors.authCheckbox.classList.contains(selectors.authCheckbox.classList[0] + this.active)) {
        selectors.authCheckboxOrigin.checked = true;
      } else {
        selectors.authCheckboxOrigin.checked = false;
      }
      selectors.authCheckbox.classList.toggle(selectors.authCheckbox.classList[0] + this.active);
      console.log(selectors.authCheckboxOrigin.checked);
    });
  }

  launcherResponse (type, data) {
    switch(type) {
      case 1:
        // первый тип, data=null, вывод двухфакторной аутентификации
        this.display = "google";
        break;
      case 2:
        // парсим response и редактируем главный экран
        let response = JSON.parse(data);
        this.selectors.mainNickname.textContent = response.user_login;
        setTimeout(() => {
          for (let i = 0; i < this.googleInputs.length; i++) {
            this.googleInputs[i].classList.add(this.googleInputs[i].classList[0] + this.access);
          }
          this.selectors.googleError.textContent = "Код верен!";
          this.selectors.rootError.classList.add(this.selectors.rootError.classList[0] + this.access);
          this.selectors.rootError.textContent = "Успешная авторизация!";
          this.rootErrorHandler(this.selectors);
          if (this.#loadTime+(this.#minLoadTime-1000) > this.time) {
            setTimeout(() => {
              this.display = "main";
            }, this.#loadTime+(this.#minLoadTime-1000)-this.time);
          } else {
            setTimeout(() => {
              this.display = "main";
            }, 1000);
          }
          this.selectors.googleError.classList.add(this.selectors.googleError.classList[0] + this.access);
        }, 1500);
        if (response.characters !== null) {
          for (let i = 0; i != response.characters.length; i++) {
            this.characterSelectOrigin.innerHTML += `
              <option class="main-display__select-options" value=${response.characters[i].name}>${response.characters[i].name}</option>
            `;
            this.selectors.characterSelectList.innerHTML += `
              <li class="main-display__select-item" data-game=${response.characters[i].game}>
                <img src="./images/character-image.png" alt="#" class="main-display__select-image">
                <div class="main-display__select-info">
                  <span class="main-display__select-name">${response.characters[i].name}</span>
                  <span class="main-display__select-status">${response.characters[i].status}</span>
                </div>
              </li>
            `;
          }
        }
        let characterSelectItemsNew = document.querySelectorAll(".main-display__select-item");
        let characterSelectItemsNewArray = Array.prototype.slice.call(characterSelectItemsNew);
        let characterSelectNameNew = document.querySelectorAll(".main-display__select-name")
        let characterSelectOriginOptionsNew = document.querySelectorAll(".main-display__select-options");

        console.log(characterSelectItemsNew);
        console.log(characterSelectOriginOptionsNew);
        this.mainSelectHandler(this.selectors, characterSelectItemsNewArray, characterSelectOriginOptionsNew, characterSelectNameNew);

//        this.selectors.notificationCardsBlock.innerHTML = `
//          <h2 class="notification-content__title">Уведомлений нет</h2>
//        `;
//        this.selectors.notificationRead.classList.remove(this.selectors.notificationRead.classList[0] + this.active);
//        this.selectors.notificationOpen.classList.remove(this.selectors.notificationOpen.classList[0] + this.active);
        break;
      case 3:
        // парсим response и подгружаем уведомления
        let notificationResponse = JSON.parse(data);
        console.log("Данные третьего кейса: ", notificationResponse);
        if (notificationResponse !== null) {
          if (this.selectors.notificationCardsBlock.hasChildNodes()) {
            this.selectors.notificationCardsBlock.removeChild(this.selectors.notificationCardsBlock.childNodes[0]);
          }
          for (let i = 0; i != notificationResponse.length; i++) {
            let date = this.timeConverter(notificationResponse[i].date);
            let classes = `notification-card notification-content__card ${(notificationResponse[i].status == -1) ? 'notification-card--active' : ""}`;

            let card = document.createElement("a");
            card.className = `${classes}`;
            card.innerHTML = `
              <span class="notification-card__date">${date}</span>
              <p class="notification-card__info">${notificationResponse[i].text}</p>
            `;
            this.selectors.notificationCardsBlock.prepend(card);
          }
          if (notificationResponse.some(item => item.status == -1)) {
            this.selectors.notificationOpen.classList.add(this.selectors.notificationOpen.classList[0] + this.active);
            this.selectors.notificationRead.classList.add(this.selectors.notificationRead.classList[0] + this.active);
          } else {
            this.selectors.notificationOpen.classList.remove(this.selectors.notificationOpen.classList[0] + this.active);
            this.selectors.notificationRead.classList.remove(this.selectors.notificationRead.classList[0] + this.active);
          }
          let notifications = document.querySelectorAll(".notification-card");
          this.notificationCardsArray = Array.prototype.slice.call(notifications);
          this.notificationsHandler(this.selectors);
        } else {
          this.selectors.notificationCardsBlock.innerHTML = `<h2 class="notification-content__title">Уведомлений нет</h2>`;
        }
        break;
    }
  }
  launcherError(id, error) {
    switch(id) {
      case 101:
        this.selectors.rootError.textContent = "Неверный никнейм!";
        this.authInputErrorHandler(this.auth[0], this.auth[1]);
        this.rootErrorHandler(this.selectors);
      case 102:
        // несуществующий личный кабинет (экран авторизации)
        this.selectors.rootError.textContent = "Неверный никнейм!";
        this.authInputErrorHandler(this.auth[0]);
        this.rootErrorHandler(this.selectors);
        break;
      case 103:
        // сессия уже активна (экран авторизации)
        this.selectors.rootError.textContent = "Сессия уже активна!";
        this.rootErrorHandler(this.selectors);
        break;
      case 104:
        // неверный пароль (экран авторизации)
        this.selectors.rootError.textContent = "Введён неверный пароль!";
        this.authInputErrorHandler(this.auth[1]);
        this.rootErrorHandler(this.selectors);
        break;
      case 105:
        setTimeout(() => {
          this.selectors.googleError.classList.add(this.selectors.googleError.classList[0] + this.active);
          for (let i = 0; i < this.googleInputs.length; i++) {
            this.googleInputs[i].classList.toggle(this.googleInputs[i].classList[0] + this.error);
            this.googleInputs[i].value = "";
            this.googleInputs[i].removeAttribute("disabled", "disabled");
          }
          this.sendCode = "";
          this.googleInputs[0].focus();
        }, 1500);
        break;
      case 107:
        // произошла ошибка отправки кода (экран двухфакторки)
        setTimeout(() => {
          this.selectors.googleError.textContent = "Ошибка отправки кода!";
          this.selectors.googleError.classList.add(this.selectors.googleError.classList[0] + this.active);
          for (let i = 0; i < this.googleInputs.length; i++) {
            this.googleInputs[i].classList.toggle(this.googleInputs[i].classList[0] + this.error);
            this.googleInputs[i].value = "";
            this.googleInputs[i].removeAttribute("disabled", "disabled");
          }
          this.sendCode = "";
          this.googleInputs[0].focus();
        }, 1500);
        break;
    }
  }
  mainSelectHandler(selectors, itemsArray, originOptions, characterNames) {
    selectors.characterSelectCustom.addEventListener("click", event => {
      event.preventDefault();
      if (itemsArray.some(item => item.classList.contains("active"))) {
        itemsArray.forEach(item => {
          item.classList.remove("active");
        });
        originOptions[0].setAttribute("selected", "selected");
      }
      setTimeout(() => {
        selectors.characterSelectCustom.classList.toggle("active");
        if (selectors.characterSelectCustom.classList.contains("active")) {
          selectors.startGameButton.classList.add("hidden");
          selectors.startGameButton.classList.remove("active");
          setTimeout(() => {
            setTimeout(() => {
              selectors.characterSelectContent.classList.toggle("active");
              selectors.characterSelectContent.classList.toggle("hidden");
            }, 150);
            selectors.characterSelectContent.classList.toggle("dnone");
          }, 150);
        } else {
          selectors.characterSelectContent.classList.toggle("active");
          selectors.characterSelectContent.classList.toggle("hidden");
          setTimeout(() => {
            selectors.startGameButton.classList.add("active");
            selectors.startGameButton.classList.remove("hidden");
            selectors.characterSelectContent.classList.toggle("dnone");
          }, 300);
        }
      }, 150);
    });

    itemsArray.forEach((item, index) => {
      item.addEventListener("click", () => {
        if (item.getAttribute("data-game") == "false") {
          this.selectors.startGameButton.setAttribute("disabled", "disabled");
        } else if (item.getAttribute("data-game") == "true") {
          this.selectors.startGameButton.removeAttribute("disabled");
        }
        originOptions[index].removeAttribute("selected");
        originOptions[index + 1].setAttribute("selected", "selected");
        if (originOptions[index + 1].hasAttribute("selected")) {
          originOptions[index + 1].removeAttribute("selected");
          originOptions[index + 1].setAttribute("selected", "selected");
        }
        item.classList.toggle("active");
        console.log(this.characterSelectOrigin.value);
        selectors.characterSelectCustom.textContent = characterNames[index].textContent;
        // прячем кастомный селект
        setTimeout(() => {
          selectors.characterSelectCustom.classList.remove("active");
          selectors.characterSelectContent.classList.remove("active");
          selectors.characterSelectContent.classList.add("hidden");
          setTimeout(() => {
            selectors.characterSelectContent.classList.add("dnone");
            setTimeout(() => {
              selectors.startGameButton.classList.add("active");
              selectors.startGameButton.classList.remove("hidden");
            }, 150);
          }, 150);
        }, 750);
      });
    });
  }
  rootErrorHandler(selectors) {
    selectors.rootError.classList.add(selectors.rootError.classList[0] + this.active);
    setTimeout(() => {
      selectors.rootError.classList.remove(selectors.rootError.classList[0] + this.active);
    }, 2000);
  }
  authInputErrorHandler(input, ...args) {
    input.classList.add(input.classList[0] + this.error);
    if (args[0] != undefined) args[0].classList.add(args[0].classList[0] + this.error);
    setTimeout(() => {
      input.classList.remove(input.classList[0] + this.error);
      if (args[0] != undefined) args[0].classList.remove(args[0].classList[0] + this.error);
    }, 2000);
  }
  get time() {
    return new Date().getTime();
  }

}