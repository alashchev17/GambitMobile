class App extends View {
  #session = null;
  #loadTime = 0;
  #minLoadTime = 3000;
  // login(input, check) {
  //   let login = input[0];
  //   let password = input[1];

  //   let response = AppAction.login(login, password, check);
  // }
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
   /* if (this.displayName == undefined) {
      this.display = "first";
      setTimeout(() => {
        this.display = this.#loadDiplay;
      }, 3500);
    } else {
      this.#loadDiplay = 'intro';
    }*/

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
        this.selectors.rootError.classList.add(this.selectors.rootError.classList[0] + this.access);
        this.selectors.rootError.textContent = "Успешная авторизация!";
        this.rootErrorHandler(this.selectors);
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
          if (this.#loadTime+(this.#minLoadTime-250) > this.time) {
            setTimeout(() => {
              this.display = "main";
            }, this.#loadTime+(this.#minLoadTime-250)-this.time);
          } else {
            setTimeout(() => {
              this.display = "main";
            }, 250);
          }
          this.selectors.googleError.classList.add(this.selectors.googleError.classList[0] + this.access);
        }, 250);
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
        let characterSelectItemsNew = document.querySelectorAll(".main-display__select-item");
        let characterSelectItemsNewArray = Array.prototype.slice.call(characterSelectItemsNew);
        let characterSelectNameNew = document.querySelectorAll(".main-display__select-name")
        let characterSelectOriginOptionsNew = document.querySelectorAll(".main-display__select-options");

        console.log(characterSelectItemsNew);
        console.log(characterSelectOriginOptionsNew);
        this.mainSelectHandler(this.selectors, characterSelectItemsNewArray, characterSelectOriginOptionsNew, characterSelectNameNew);
//        characterSelectCustom.addEventListener("click", event => {
//          event.preventDefault();
//          if (this.characterSelectItemsArray.some(item => item.classList.contains("active"))) {
//            characterSelectItems.forEach(item => {
//              item.classList.remove("active");
//            });
//            characterSelectOriginOptions[0].setAttribute("selected", "selected");
//          }
//          setTimeout(() => {
//            characterSelectCustom.classList.toggle("active");
//            if (characterSelectCustom.classList.contains("active")) {
//              mainDisplayButton.classList.add("hidden");
//              mainDisplayButton.classList.remove("active");
//              setTimeout(() => {
//                setTimeout(() => {
//                  characterSelectContent.classList.toggle("dnone");
//                }, 150);
//                characterSelectContent.classList.toggle("active");
//                characterSelectContent.classList.toggle("hidden");
//              }, 50);
//            } else {
//              characterSelectContent.classList.toggle("active");
//              characterSelectContent.classList.toggle("hidden");
//              setTimeout(() => {
//                mainDisplayButton.classList.add("active");
//                mainDisplayButton.classList.remove("hidden");
//                characterSelectContent.classList.toggle("dnone");
//              }, 300);
//            }
//          }, 150);
//        });
//        characterSelectItems.forEach((item, index) => {
//          item.addEventListener("click", () => {
//            if (item.getAttribute("data-game") == false) {
//              startGameButton.setAttribute("disabled", "disabled");
//            } else if (item.getAttribute("data-game") == true) {
//              startGameButton.removeAttribute("disabled");
//            }
//            characterSelectOriginOptions[index].removeAttribute("selected");
//            characterSelectOriginOptions[index + 1].setAttribute("selected", "selected");
//            if (characterSelectOriginOptions[index + 1].hasAttribute("selected")) {
//              characterSelectOriginOptions[index + 1].removeAttribute("selected");
//              characterSelectOriginOptions[index + 1].setAttribute("selected", "selected");
//            }
//            item.classList.toggle("active");
//            console.log(characterSelectOrigin.value);
//            characterSelectCustom.textContent = characterSelectName[index].textContent;
//            // прячем кастомный селект
//            setTimeout(() => {
//              characterSelectCustom.classList.remove("active");
//              characterSelectContent.classList.remove("active");
//              characterSelectContent.classList.add("hidden");
//              setTimeout(() => {
//                characterSelectContent.classList.add("dnone");
//                setTimeout(() => {
//                  mainDisplayButton.classList.add("active");
//                  mainDisplayButton.classList.remove("hidden");
//                }, 150);
//              }, 150);
//            }, 750);
//          });
//        });
        /* пройдена аутентификация, data={
          "user_id": 66902,
          "user_login": "testlc", --> логин, который отображается в хедере
          "characters": --> список персонажей для рендера в селекте
            [
              {
                "game": true,
                "name": "testAcc_One",
                "skin": 0,
                "id": 480423,
                "status": "Одобрен"
              },
              {
                "game": true,
                "name": "test_Two",
                "skin": 0,
                "id": 480424,
                "status": "Одобрен"
              }
            ]
        }
        */
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
          this.googleCode = "";
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
          this.googleCode = "";
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

    itemsArray.forEach((item, index) => {
      item.addEventListener("click", () => {
        if (item.getAttribute("data-game") == false) {
          this.selectors.startGameButton.setAttribute("disabled", "disabled");
        } else if (item.getAttribute("data-game") == true) {
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
              selectors.mainDisplayButton.classList.add("active");
              selectors.mainDisplayButton.classList.remove("hidden");
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
}