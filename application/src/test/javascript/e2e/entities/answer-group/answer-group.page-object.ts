import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AnswerGroupComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-answer-group div table .btn-danger'));
  title = element.all(by.css('jhi-answer-group div h2#page-heading span')).first();

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

export class AnswerGroupUpdatePage {
  pageTitle = element(by.id('jhi-answer-group-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  answerResponseSelect = element(by.id('field_answerResponse'));
  questionGroupSelect = element(by.id('field_questionGroup'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async answerResponseSelectLastOption(timeout?: number) {
    await this.answerResponseSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async answerResponseSelectOption(option) {
    await this.answerResponseSelect.sendKeys(option);
  }

  getAnswerResponseSelect(): ElementFinder {
    return this.answerResponseSelect;
  }

  async getAnswerResponseSelectedOption() {
    return await this.answerResponseSelect.element(by.css('option:checked')).getText();
  }

  async questionGroupSelectLastOption(timeout?: number) {
    await this.questionGroupSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async questionGroupSelectOption(option) {
    await this.questionGroupSelect.sendKeys(option);
  }

  getQuestionGroupSelect(): ElementFinder {
    return this.questionGroupSelect;
  }

  async getQuestionGroupSelectedOption() {
    return await this.questionGroupSelect.element(by.css('option:checked')).getText();
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

export class AnswerGroupDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-answerGroup-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-answerGroup'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
