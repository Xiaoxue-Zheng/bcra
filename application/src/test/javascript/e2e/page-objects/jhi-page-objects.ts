import { element, by, ElementFinder } from 'protractor';
import { DB } from '../db-commands/DB';

export class NavBarPage {
  studyIdsMenu = element(by.css('[routerLink="study-id"]'));
  accountMenu = element(by.id('account-menu'));
  adminMenu: ElementFinder;
  signIn = element(by.id('login'));
  register = element(by.css('[routerLink="register"]'));
  signOut = element(by.id('logout'));
  passwordMenu = element(by.css('[routerLink="password"]'));
  settingsMenu = element(by.css('[routerLink="settings"]'));

  constructor(asAdmin?: Boolean) {
    if (asAdmin) {
      this.adminMenu = element(by.id('admin-menu'));
    }
  }

  async clickOnAccountMenu() {
    await this.accountMenu.click();
  }

  async clickOnAdminMenu() {
    await this.adminMenu.click();
  }

  async clickOnSignIn() {
    await this.signIn.click();
  }

  async clickOnRegister() {
    await this.signIn.click();
  }

  async clickOnSignOut() {
    await this.signOut.click();
  }

  async clickOnPasswordMenu() {
    await this.passwordMenu.click();
  }

  async clickOnSettingsMenu() {
    await this.settingsMenu.click();
  }

  async clickOnStudyIds() {
    await element(by.css('[routerLink="study-id"]')).click();
  }

  async clickOnAdmin(entityName: string) {
    await element(by.css('[routerLink="admin/' + entityName + '"]')).click();
  }

  async getSignInPage() {
    await this.clickOnAccountMenu();
    await this.clickOnSignIn();
    return new SignInPage();
  }
  async getPasswordPage() {
    await this.clickOnAccountMenu();
    await this.clickOnPasswordMenu();
    return new PasswordPage();
  }

  async getSettingsPage() {
    await this.clickOnAccountMenu();
    await this.clickOnSettingsMenu();
    return new SettingsPage();
  }

  async goToSignInPage() {
    await this.clickOnAccountMenu();
    await this.clickOnSignIn();
  }

  async goToPasswordMenu() {
    await this.clickOnAccountMenu();
    await this.clickOnPasswordMenu();
  }

  async autoSignOut() {
    await this.clickOnAccountMenu();
    await this.clickOnSignOut();
  }
}

export class SignInPage {
  username = element(by.id('username'));
  password = element(by.id('password'));
  pin = element(by.id('pin'));
  loginButton = element(by.id('sign-in-button'));
  confirmPinButton = element(by.id('confirm-pin-button'));
  db = new DB();

  async setUserName(username) {
    await this.username.sendKeys(username);
  }

  async getUserName() {
    return this.username.getAttribute('value');
  }

  async clearUserName() {
    await this.username.clear();
  }

  async setPassword(password) {
    await this.password.sendKeys(password);
  }

  async getPassword() {
    return this.password.getAttribute('value');
  }

  async clearPassword() {
    await this.password.clear();
  }

  async setPin(pin) {
    await this.pin.sendKeys(pin);
  }

  async getPin() {
    return this.pin.getAttribute('value');
  }

  async clearPin() {
    await this.pin.clear();
  }

  async twoFactorSignIn(username: string, password: string) {
    await this.setUserName(username);
    await this.setPassword(password);
    await this.login();
    const sql = 'select pin from two_factor_authentication where user_id= (select id from jhi_user where login = $1)';
    const pinVal = await this.db.execute(sql, [username]).then(res => {
      return res.pin;
    });
    await this.setPin(pinVal);
    await this.confirmPin();
  }

  async twoFactorSignInWithPin(username: string, password: string, pinVal: string) {
    await this.setUserName(username);
    await this.setPassword(password);
    await this.login();
    await this.setPin(pinVal);
    await this.confirmPin();
  }

  async autoSignNormalUserInUsing(username: string, password: string) {
    await this.setUserName(username);
    await this.setPassword(password);
    await this.login();
  }

  async login() {
    await this.loginButton.click();
    // have to wait hibernate refresh cache
    await this.delay(1000);
  }

  delay(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  async confirmPin() {
    await this.confirmPinButton.click();
  }
}
export class PasswordPage {
  currentPassword = element(by.id('currentPassword'));
  password = element(by.id('newPassword'));
  confirmPassword = element(by.id('confirmPassword'));
  saveButton = element(by.css('button[type=submit]'));
  title = element.all(by.css('h2')).first();

  async setCurrentPassword(password) {
    await this.currentPassword.sendKeys(password);
  }

  async setPassword(password) {
    await this.password.sendKeys(password);
  }

  async getPassword() {
    return this.password.getAttribute('value');
  }

  async clearPassword() {
    await this.password.clear();
  }

  async setConfirmPassword(confirmPassword) {
    await this.confirmPassword.sendKeys(confirmPassword);
  }

  async getConfirmPassword() {
    return this.confirmPassword.getAttribute('value');
  }

  async clearConfirmPassword() {
    await this.confirmPassword.clear();
  }

  async getTitle() {
    return this.title.getText();
  }

  async save() {
    await this.saveButton.click();
  }
}

export class SettingsPage {
  firstName = element(by.id('firstName'));
  lastName = element(by.id('lastName'));
  email = element(by.id('email'));
  saveButton = element(by.css('button[type=submit]'));
  title = element.all(by.css('h2')).first();

  async setFirstName(firstName) {
    await this.firstName.sendKeys(firstName);
  }

  async getFirstName() {
    return this.firstName.getAttribute('value');
  }

  async clearFirstName() {
    await this.firstName.clear();
  }

  async setLastName(lastName) {
    await this.lastName.sendKeys(lastName);
  }

  async getLastName() {
    return this.lastName.getAttribute('value');
  }

  async clearLastName() {
    await this.lastName.clear();
  }

  async setEmail(email) {
    await this.email.sendKeys(email);
  }

  async getEmail() {
    return this.email.getAttribute('value');
  }

  async clearEmail() {
    await this.email.clear();
  }

  async getTitle() {
    return this.title.getText();
  }

  async save() {
    await this.saveButton.click();
  }
}
