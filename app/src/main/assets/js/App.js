class App extends View {
  #session = null;
  #debug = true;
  // login(input, check) {
  //   let login = input[0];
  //   let password = input[1];

  //   let response = AppAction.login(login, password, check);
  // }
  constructor() {
    super();
    this.displaysInit(this.#debug);
    this.codeHandler("111111");
    this.checkboxData(this.selectors);
  }
  displaysInit(debug) {
    if (debug) {
      this.display = "first";
      setTimeout(() => {
        this.display = "intro";
      }, 2500);
    } else {
      this.display = "first";
      setTimeout(() => {
        this.display = "loading";
      }, 2500);
    }
  }
  codeHandler(code) {
    this.googleInputs[5].addEventListener("keyup", () => {
      if (this.googleInputs[5].value !== "") {
        for (let i = 0; i < this.googleInputs.length; i++) {
          this.googleCode += this.googleInputs[i].value;
          this.googleInputs[i].setAttribute("disabled", "disabled");
        }
        console.warn(this.googleCode);
        if (this.googleCode !== code) {
          // таймаут для имитации запроса на сервер и получения ответа от него
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
        } else if (this.googleCode == code) {
          // тестовый подтверждающий код и таймаут для имитации запроса на сервер
          setTimeout(() => {
            for (let i = 0; i < this.googleInputs.length; i++) {
              this.googleInputs[i].classList.add(this.googleInputs[i].classList[0] + this.access);
            }
            this.selectors.googleError.textContent = "Код верен!";
            this.selectors.googleError.classList.add(this.selectors.googleError.classList[0] + this.access);
            setTimeout(() => {
              this.display = "main";
            }, 1500);
          }, 1500);
        }
      }
    });
  }
  checkboxData(selectors) {
    selectors.checkboxSpan.addEventListener("click", () => {
      if (!selectors.authCheckbox.classList.contains("checkbox__label--active")) {
        selectors.authCheckboxOrigin.checked = true;
      } else {
        selectors.authCheckboxOrigin.checked = false;
      }
      selectors.authCheckbox.classList.toggle("checkbox__label--active");
      console.log(selectors.authCheckboxOrigin.checked);
    });
  }
}