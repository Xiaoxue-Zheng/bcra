import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class CheckboxAnswerItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-checkbox-answer-item div table .btn-danger'));
  title = element.all(by.css('jhi-checkbox-answer-item div h2#page-heading span')).first();

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

export class CheckboxAnswerItemUpdatePage {
  pageTitle = element(by.id('jhi-checkbox-answer-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  checkboxAnswerSelect = element(by.id('field_checkboxAnswer'));
  checkboxQuestionItemSelect = element(by.id('field_checkboxQuestionItem'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async checkboxAnswerSelectLastOption(timeout?: number) {
    await this.checkboxAnswerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async checkboxAnswerSelectOption(option) {
    await this.checkboxAnswerSelect.sendKeys(option);
  }

  getCheckboxAnswerSelect(): ElementFinder {
    return this.checkboxAnswerSelect;
  }

  async getCheckboxAnswerSelectedOption() {
    return await this.checkboxAnswerSelect.element(by.css('option:checked')).getText();
  }

  async checkboxQuestionItemSelectLastOption(timeout?: number) {
    await this.checkboxQuestionItemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async checkboxQuestionItemSelectOption(option) {
    await this.checkboxQuestionItemSelect.sendKeys(option);
  }

  getCheckboxQuestionItemSelect(): ElementFinder {
    return this.checkboxQuestionItemSelect;
  }

  async getCheckboxQuestionItemSelectedOption() {
    return await this.checkboxQuestionItemSelect.element(by.css('option:checked')).getText();
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

export class CheckboxAnswerItemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-checkboxAnswerItem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-checkboxAnswerItem'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
