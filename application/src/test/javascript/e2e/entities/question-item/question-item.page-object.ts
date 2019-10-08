import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class QuestionItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-question-item div table .btn-danger'));
  title = element.all(by.css('jhi-question-item div h2#page-heading span')).first();

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

export class QuestionItemUpdatePage {
  pageTitle = element(by.id('jhi-question-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  identifierSelect = element(by.id('field_identifier'));
  orderInput = element(by.id('field_order'));
  labelInput = element(by.id('field_label'));
  questionSelect = element(by.id('field_question'));

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

  async setLabelInput(label) {
    await this.labelInput.sendKeys(label);
  }

  async getLabelInput() {
    return await this.labelInput.getAttribute('value');
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

export class QuestionItemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-questionItem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-questionItem'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
