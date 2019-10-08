import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AnswerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-answer div table .btn-danger'));
  title = element.all(by.css('jhi-answer div h2#page-heading span')).first();

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

export class AnswerUpdatePage {
  pageTitle = element(by.id('jhi-answer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  numberInput = element(by.id('field_number'));
  unitsSelect = element(by.id('field_units'));
  answerGroupSelect = element(by.id('field_answerGroup'));
  questionSelect = element(by.id('field_question'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNumberInput(number) {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput() {
    return await this.numberInput.getAttribute('value');
  }

  async setUnitsSelect(units) {
    await this.unitsSelect.sendKeys(units);
  }

  async getUnitsSelect() {
    return await this.unitsSelect.element(by.css('option:checked')).getText();
  }

  async unitsSelectLastOption(timeout?: number) {
    await this.unitsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async answerGroupSelectLastOption(timeout?: number) {
    await this.answerGroupSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async answerGroupSelectOption(option) {
    await this.answerGroupSelect.sendKeys(option);
  }

  getAnswerGroupSelect(): ElementFinder {
    return this.answerGroupSelect;
  }

  async getAnswerGroupSelectedOption() {
    return await this.answerGroupSelect.element(by.css('option:checked')).getText();
  }

  async questionSelectLastOption(timeout?: number) {
    await this.questionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async questionSelectOption(option) {
    await this.questionSelect.sendKeys(option);
  }

  getQuestionSelect(): ElementFinder {
    return this.questionSelect;
  }

  async getQuestionSelectedOption() {
    return await this.questionSelect.element(by.css('option:checked')).getText();
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

export class AnswerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-answer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-answer'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
