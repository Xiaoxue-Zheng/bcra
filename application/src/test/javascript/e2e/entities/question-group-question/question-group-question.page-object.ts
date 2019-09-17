import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class QuestionGroupQuestionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-question-group-question div table .btn-danger'));
  title = element.all(by.css('jhi-question-group-question div h2#page-heading span')).first();

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

export class QuestionGroupQuestionUpdatePage {
  pageTitle = element(by.id('jhi-question-group-question-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  uuidInput = element(by.id('field_uuid'));
  questionGroupUuidInput = element(by.id('field_questionGroupUuid'));
  questionUuidInput = element(by.id('field_questionUuid'));
  orderInput = element(by.id('field_order'));
  questionGroupSelect = element(by.id('field_questionGroup'));
  questionSelect = element(by.id('field_question'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setUuidInput(uuid) {
    await this.uuidInput.sendKeys(uuid);
  }

  async getUuidInput() {
    return await this.uuidInput.getAttribute('value');
  }

  async setQuestionGroupUuidInput(questionGroupUuid) {
    await this.questionGroupUuidInput.sendKeys(questionGroupUuid);
  }

  async getQuestionGroupUuidInput() {
    return await this.questionGroupUuidInput.getAttribute('value');
  }

  async setQuestionUuidInput(questionUuid) {
    await this.questionUuidInput.sendKeys(questionUuid);
  }

  async getQuestionUuidInput() {
    return await this.questionUuidInput.getAttribute('value');
  }

  async setOrderInput(order) {
    await this.orderInput.sendKeys(order);
  }

  async getOrderInput() {
    return await this.orderInput.getAttribute('value');
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

export class QuestionGroupQuestionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-questionGroupQuestion-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-questionGroupQuestion'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
