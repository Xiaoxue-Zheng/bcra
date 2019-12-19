import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ParticipantComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-participant div table .btn-danger'));
  title = element.all(by.css('jhi-participant div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class ParticipantUpdatePage {
  pageTitle = element(by.id('jhi-participant-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  registerDatetimeInput = element(by.id('field_registerDatetime'));
  lastLoginDatetimeInput = element(by.id('field_lastLoginDatetime'));
  userSelect = element(by.id('field_user'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setRegisterDatetimeInput(registerDatetime) {
    await this.registerDatetimeInput.sendKeys(registerDatetime);
  }

  async getRegisterDatetimeInput() {
    return await this.registerDatetimeInput.getAttribute('value');
  }

  async setLastLoginDatetimeInput(lastLoginDatetime) {
    await this.lastLoginDatetimeInput.sendKeys(lastLoginDatetime);
  }

  async getLastLoginDatetimeInput() {
    return await this.lastLoginDatetimeInput.getAttribute('value');
  }

  async userSelectLastOption(timeout?: number) {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option) {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption() {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ParticipantDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-participant-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-participant'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
