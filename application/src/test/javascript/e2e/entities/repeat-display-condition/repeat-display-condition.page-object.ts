import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class RepeatDisplayConditionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-repeat-display-condition div table .btn-danger'));
  title = element.all(by.css('jhi-repeat-display-condition div h2#page-heading span')).first();

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

export class RepeatDisplayConditionUpdatePage {
  pageTitle = element(by.id('jhi-repeat-display-condition-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  repeatQuestionSelect = element(by.id('field_repeatQuestion'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async repeatQuestionSelectLastOption(timeout?: number) {
    await this.repeatQuestionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async repeatQuestionSelectOption(option) {
    await this.repeatQuestionSelect.sendKeys(option);
  }

  getRepeatQuestionSelect(): ElementFinder {
    return this.repeatQuestionSelect;
  }

  async getRepeatQuestionSelectedOption() {
    return await this.repeatQuestionSelect.element(by.css('option:checked')).getText();
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

export class RepeatDisplayConditionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-repeatDisplayCondition-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-repeatDisplayCondition'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
