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
  displayConditionTypeSelect = element(by.id('field_displayConditionType'));
  conditionSectionIdentifierSelect = element(by.id('field_conditionSectionIdentifier'));
  conditionQuestionIdentifierSelect = element(by.id('field_conditionQuestionIdentifier'));
  conditionQuestionItemIdentifierSelect = element(by.id('field_conditionQuestionItemIdentifier'));
  displayQuestionSectionSelect = element(by.id('field_displayQuestionSection'));
  displayQuestionSelect = element(by.id('field_displayQuestion'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDisplayConditionTypeSelect(displayConditionType) {
    await this.displayConditionTypeSelect.sendKeys(displayConditionType);
  }

  async getDisplayConditionTypeSelect() {
    return await this.displayConditionTypeSelect.element(by.css('option:checked')).getText();
  }

  async displayConditionTypeSelectLastOption(timeout?: number) {
    await this.displayConditionTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setConditionSectionIdentifierSelect(conditionSectionIdentifier) {
    await this.conditionSectionIdentifierSelect.sendKeys(conditionSectionIdentifier);
  }

  async getConditionSectionIdentifierSelect() {
    return await this.conditionSectionIdentifierSelect.element(by.css('option:checked')).getText();
  }

  async conditionSectionIdentifierSelectLastOption(timeout?: number) {
    await this.conditionSectionIdentifierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setConditionQuestionIdentifierSelect(conditionQuestionIdentifier) {
    await this.conditionQuestionIdentifierSelect.sendKeys(conditionQuestionIdentifier);
  }

  async getConditionQuestionIdentifierSelect() {
    return await this.conditionQuestionIdentifierSelect.element(by.css('option:checked')).getText();
  }

  async conditionQuestionIdentifierSelectLastOption(timeout?: number) {
    await this.conditionQuestionIdentifierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setConditionQuestionItemIdentifierSelect(conditionQuestionItemIdentifier) {
    await this.conditionQuestionItemIdentifierSelect.sendKeys(conditionQuestionItemIdentifier);
  }

  async getConditionQuestionItemIdentifierSelect() {
    return await this.conditionQuestionItemIdentifierSelect.element(by.css('option:checked')).getText();
  }

  async conditionQuestionItemIdentifierSelectLastOption(timeout?: number) {
    await this.conditionQuestionItemIdentifierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async displayQuestionSectionSelectLastOption(timeout?: number) {
    await this.displayQuestionSectionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async displayQuestionSectionSelectOption(option) {
    await this.displayQuestionSectionSelect.sendKeys(option);
  }

  getDisplayQuestionSectionSelect(): ElementFinder {
    return this.displayQuestionSectionSelect;
  }

  async getDisplayQuestionSectionSelectedOption() {
    return await this.displayQuestionSectionSelect.element(by.css('option:checked')).getText();
  }

  async displayQuestionSelectLastOption(timeout?: number) {
    await this.displayQuestionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async displayQuestionSelectOption(option) {
    await this.displayQuestionSelect.sendKeys(option);
  }

  getDisplayQuestionSelect(): ElementFinder {
    return this.displayQuestionSelect;
  }

  async getDisplayQuestionSelectedOption() {
    return await this.displayQuestionSelect.element(by.css('option:checked')).getText();
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
