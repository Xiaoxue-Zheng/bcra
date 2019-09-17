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
  questionGroupSelect = element(by.id('field_questionGroup'));

  async getPageTitle() {
    return this.pageTitle.getText();
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
