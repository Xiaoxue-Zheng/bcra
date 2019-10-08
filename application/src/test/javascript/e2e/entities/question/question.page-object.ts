import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class QuestionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-question div table .btn-danger'));
  title = element.all(by.css('jhi-question div h2#page-heading span')).first();

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

export class QuestionUpdatePage {
  pageTitle = element(by.id('jhi-question-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  identifierSelect = element(by.id('field_identifier'));
  typeSelect = element(by.id('field_type'));
  orderInput = element(by.id('field_order'));
  textInput = element(by.id('field_text'));
  minimumInput = element(by.id('field_minimum'));
  maximumInput = element(by.id('field_maximum'));
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

  async setTypeSelect(type) {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect() {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(timeout?: number) {
    await this.typeSelect
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

  async setTextInput(text) {
    await this.textInput.sendKeys(text);
  }

  async getTextInput() {
    return await this.textInput.getAttribute('value');
  }

  async setMinimumInput(minimum) {
    await this.minimumInput.sendKeys(minimum);
  }

  async getMinimumInput() {
    return await this.minimumInput.getAttribute('value');
  }

  async setMaximumInput(maximum) {
    await this.maximumInput.sendKeys(maximum);
  }

  async getMaximumInput() {
    return await this.maximumInput.getAttribute('value');
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

export class QuestionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-question-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-question'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
