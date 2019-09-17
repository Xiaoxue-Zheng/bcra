import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class RadioAnswerItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-radio-answer-item div table .btn-danger'));
  title = element.all(by.css('jhi-radio-answer-item div h2#page-heading span')).first();

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

export class RadioAnswerItemUpdatePage {
  pageTitle = element(by.id('jhi-radio-answer-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  radioAnswerSelect = element(by.id('field_radioAnswer'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async radioAnswerSelectLastOption(timeout?: number) {
    await this.radioAnswerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async radioAnswerSelectOption(option) {
    await this.radioAnswerSelect.sendKeys(option);
  }

  getRadioAnswerSelect(): ElementFinder {
    return this.radioAnswerSelect;
  }

  async getRadioAnswerSelectedOption() {
    return await this.radioAnswerSelect.element(by.css('option:checked')).getText();
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

export class RadioAnswerItemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-radioAnswerItem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-radioAnswerItem'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
