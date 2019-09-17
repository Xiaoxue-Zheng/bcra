import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class CheckboxQuestionItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-checkbox-question-item div table .btn-danger'));
  title = element.all(by.css('jhi-checkbox-question-item div h2#page-heading span')).first();

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

export class CheckboxQuestionItemUpdatePage {
  pageTitle = element(by.id('jhi-checkbox-question-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  uuidInput = element(by.id('field_uuid'));
  questionUuidInput = element(by.id('field_questionUuid'));
  labelInput = element(by.id('field_label'));
  descriptorInput = element(by.id('field_descriptor'));
  checkboxQuestionSelect = element(by.id('field_checkboxQuestion'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setUuidInput(uuid) {
    await this.uuidInput.sendKeys(uuid);
  }

  async getUuidInput() {
    return await this.uuidInput.getAttribute('value');
  }

  async setQuestionUuidInput(questionUuid) {
    await this.questionUuidInput.sendKeys(questionUuid);
  }

  async getQuestionUuidInput() {
    return await this.questionUuidInput.getAttribute('value');
  }

  async setLabelInput(label) {
    await this.labelInput.sendKeys(label);
  }

  async getLabelInput() {
    return await this.labelInput.getAttribute('value');
  }

  async setDescriptorInput(descriptor) {
    await this.descriptorInput.sendKeys(descriptor);
  }

  async getDescriptorInput() {
    return await this.descriptorInput.getAttribute('value');
  }

  async checkboxQuestionSelectLastOption(timeout?: number) {
    await this.checkboxQuestionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async checkboxQuestionSelectOption(option) {
    await this.checkboxQuestionSelect.sendKeys(option);
  }

  getCheckboxQuestionSelect(): ElementFinder {
    return this.checkboxQuestionSelect;
  }

  async getCheckboxQuestionSelectedOption() {
    return await this.checkboxQuestionSelect.element(by.css('option:checked')).getText();
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

export class CheckboxQuestionItemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-checkboxQuestionItem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-checkboxQuestionItem'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
