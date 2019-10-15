import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ReferralConditionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-referral-condition div table .btn-danger'));
  title = element.all(by.css('jhi-referral-condition div h2#page-heading span')).first();

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

export class ReferralConditionUpdatePage {
  pageTitle = element(by.id('jhi-referral-condition-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  andGroupInput = element(by.id('field_andGroup'));
  typeSelect = element(by.id('field_type'));
  questionIdentifierSelect = element(by.id('field_questionIdentifier'));
  itemIdentifierSelect = element(by.id('field_itemIdentifier'));
  numberInput = element(by.id('field_number'));
  questionSelect = element(by.id('field_question'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setAndGroupInput(andGroup) {
    await this.andGroupInput.sendKeys(andGroup);
  }

  async getAndGroupInput() {
    return await this.andGroupInput.getAttribute('value');
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

  async setNumberInput(number) {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput() {
    return await this.numberInput.getAttribute('value');
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

export class ReferralConditionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-referralCondition-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-referralCondition'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
