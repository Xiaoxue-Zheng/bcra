import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AnswerItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-answer-item div table .btn-danger'));
  title = element.all(by.css('jhi-answer-item div h2#page-heading span')).first();

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

export class AnswerItemUpdatePage {
  pageTitle = element(by.id('jhi-answer-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  selectedInput = element(by.id('field_selected'));
  answerSelect = element(by.id('field_answer'));
  questionItemSelect = element(by.id('field_questionItem'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  getSelectedInput(timeout?: number) {
    return this.selectedInput;
  }

  async answerSelectLastOption(timeout?: number) {
    await this.answerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async answerSelectOption(option) {
    await this.answerSelect.sendKeys(option);
  }

  getAnswerSelect(): ElementFinder {
    return this.answerSelect;
  }

  async getAnswerSelectedOption() {
    return await this.answerSelect.element(by.css('option:checked')).getText();
  }

  async questionItemSelectLastOption(timeout?: number) {
    await this.questionItemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async questionItemSelectOption(option) {
    await this.questionItemSelect.sendKeys(option);
  }

  getQuestionItemSelect(): ElementFinder {
    return this.questionItemSelect;
  }

  async getQuestionItemSelectedOption() {
    return await this.questionItemSelect.element(by.css('option:checked')).getText();
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

export class AnswerItemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-answerItem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-answerItem'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
