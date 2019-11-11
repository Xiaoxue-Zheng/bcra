import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AnswerResponseComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-answer-response div table .btn-danger'));
  title = element.all(by.css('jhi-answer-response div h2#page-heading span')).first();

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

export class AnswerResponseUpdatePage {
  pageTitle = element(by.id('jhi-answer-response-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  stateSelect = element(by.id('field_state'));
  statusInput = element(by.id('field_status'));
  questionnaireSelect = element(by.id('field_questionnaire'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setStateSelect(state) {
    await this.stateSelect.sendKeys(state);
  }

  async getStateSelect() {
    return await this.stateSelect.element(by.css('option:checked')).getText();
  }

  async stateSelectLastOption(timeout?: number) {
    await this.stateSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setStatusInput(status) {
    await this.statusInput.sendKeys(status);
  }

  async getStatusInput() {
    return await this.statusInput.getAttribute('value');
  }

  async questionnaireSelectLastOption(timeout?: number) {
    await this.questionnaireSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async questionnaireSelectOption(option) {
    await this.questionnaireSelect.sendKeys(option);
  }

  getQuestionnaireSelect(): ElementFinder {
    return this.questionnaireSelect;
  }

  async getQuestionnaireSelectedOption() {
    return await this.questionnaireSelect.element(by.css('option:checked')).getText();
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

export class AnswerResponseDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-answerResponse-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-answerResponse'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
