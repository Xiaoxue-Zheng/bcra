import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AnswerSectionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-answer-section div table .btn-danger'));
  title = element.all(by.css('jhi-answer-section div h2#page-heading span')).first();

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

export class AnswerSectionUpdatePage {
  pageTitle = element(by.id('jhi-answer-section-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  answerResponseSelect = element(by.id('field_answerResponse'));
  questionSectionSelect = element(by.id('field_questionSection'));

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

  async questionSectionSelectLastOption(timeout?: number) {
    await this.questionSectionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async questionSectionSelectOption(option) {
    await this.questionSectionSelect.sendKeys(option);
  }

  getQuestionSectionSelect(): ElementFinder {
    return this.questionSectionSelect;
  }

  async getQuestionSectionSelectedOption() {
    return await this.questionSectionSelect.element(by.css('option:checked')).getText();
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

export class AnswerSectionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-answerSection-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-answerSection'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
