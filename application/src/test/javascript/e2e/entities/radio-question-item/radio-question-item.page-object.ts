import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class RadioQuestionItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-radio-question-item div table .btn-danger'));
  title = element.all(by.css('jhi-radio-question-item div h2#page-heading span')).first();

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

export class RadioQuestionItemUpdatePage {
  pageTitle = element(by.id('jhi-radio-question-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  uuidInput = element(by.id('field_uuid'));
  labelInput = element(by.id('field_label'));
  descriptorInput = element(by.id('field_descriptor'));
  radioQuestionSelect = element(by.id('field_radioQuestion'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setUuidInput(uuid) {
    await this.uuidInput.sendKeys(uuid);
  }

  async getUuidInput() {
    return await this.uuidInput.getAttribute('value');
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

  async radioQuestionSelectLastOption(timeout?: number) {
    await this.radioQuestionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async radioQuestionSelectOption(option) {
    await this.radioQuestionSelect.sendKeys(option);
  }

  getRadioQuestionSelect(): ElementFinder {
    return this.radioQuestionSelect;
  }

  async getRadioQuestionSelectedOption() {
    return await this.radioQuestionSelect.element(by.css('option:checked')).getText();
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

export class RadioQuestionItemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-radioQuestionItem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-radioQuestionItem'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
