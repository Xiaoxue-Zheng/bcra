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
  orderInput = element(by.id('field_order'));
  answerSectionSelect = element(by.id('field_answerSection'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setOrderInput(order) {
    await this.orderInput.sendKeys(order);
  }

  async getOrderInput() {
    return await this.orderInput.getAttribute('value');
  }

  async answerSectionSelectLastOption(timeout?: number) {
    await this.answerSectionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async answerSectionSelectOption(option) {
    await this.answerSectionSelect.sendKeys(option);
  }

  getAnswerSectionSelect(): ElementFinder {
    return this.answerSectionSelect;
  }

  async getAnswerSectionSelectedOption() {
    return await this.answerSectionSelect.element(by.css('option:checked')).getText();
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
