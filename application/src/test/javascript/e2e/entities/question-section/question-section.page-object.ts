import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class QuestionSectionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-question-section div table .btn-danger'));
  title = element.all(by.css('jhi-question-section div h2#page-heading span')).first();

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

export class QuestionSectionUpdatePage {
  pageTitle = element(by.id('jhi-question-section-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  identifierSelect = element(by.id('field_identifier'));
  orderInput = element(by.id('field_order'));
  questionnaireSelect = element(by.id('field_questionnaire'));
  questionGroupSelect = element(by.id('field_questionGroup'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setIdentifierSelect(identifier) {
    await this.identifierSelect.sendKeys(identifier);
  }

  async getIdentifierSelect() {
    return await this.identifierSelect.element(by.css('option:checked')).getText();
  }

  async identifierSelectLastOption(timeout?: number) {
    await this.identifierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setOrderInput(order) {
    await this.orderInput.sendKeys(order);
  }

  async getOrderInput() {
    return await this.orderInput.getAttribute('value');
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

export class QuestionSectionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-questionSection-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-questionSection'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
