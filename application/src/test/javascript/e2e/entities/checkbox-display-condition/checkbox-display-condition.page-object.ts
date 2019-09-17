import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class CheckboxDisplayConditionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-checkbox-display-condition div table .btn-danger'));
  title = element.all(by.css('jhi-checkbox-display-condition div h2#page-heading span')).first();

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

export class CheckboxDisplayConditionUpdatePage {
  pageTitle = element(by.id('jhi-checkbox-display-condition-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  checkboxQuestionItemSelect = element(by.id('field_checkboxQuestionItem'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async checkboxQuestionItemSelectLastOption(timeout?: number) {
    await this.checkboxQuestionItemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async checkboxQuestionItemSelectOption(option) {
    await this.checkboxQuestionItemSelect.sendKeys(option);
  }

  getCheckboxQuestionItemSelect(): ElementFinder {
    return this.checkboxQuestionItemSelect;
  }

  async getCheckboxQuestionItemSelectedOption() {
    return await this.checkboxQuestionItemSelect.element(by.css('option:checked')).getText();
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

export class CheckboxDisplayConditionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-checkboxDisplayCondition-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-checkboxDisplayCondition'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
