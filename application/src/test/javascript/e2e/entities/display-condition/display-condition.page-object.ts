import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class DisplayConditionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-display-condition div table .btn-danger'));
  title = element.all(by.css('jhi-display-condition div h2#page-heading span')).first();

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

export class DisplayConditionUpdatePage {
  pageTitle = element(by.id('jhi-display-condition-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  questionIdentifierSelect = element(by.id('field_questionIdentifier'));
  itemIdentifierSelect = element(by.id('field_itemIdentifier'));
  questionSectionSelect = element(by.id('field_questionSection'));
  questionSelect = element(by.id('field_question'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setQuestionIdentifierSelect(questionIdentifier) {
    await this.questionIdentifierSelect.sendKeys(questionIdentifier);
  }

  async getQuestionIdentifierSelect() {
    return await this.questionIdentifierSelect.element(by.css('option:checked')).getText();
  }

  async questionIdentifierSelectLastOption(timeout?: number) {
    await this.questionIdentifierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setItemIdentifierSelect(itemIdentifier) {
    await this.itemIdentifierSelect.sendKeys(itemIdentifier);
  }

  async getItemIdentifierSelect() {
    return await this.itemIdentifierSelect.element(by.css('option:checked')).getText();
  }

  async itemIdentifierSelectLastOption(timeout?: number) {
    await this.itemIdentifierSelect
      .all(by.tagName('option'))
      .last()
      .click();
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

export class DisplayConditionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-displayCondition-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-displayCondition'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
