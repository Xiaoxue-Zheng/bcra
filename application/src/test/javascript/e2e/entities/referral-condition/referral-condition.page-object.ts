import { element, by, ElementFinder } from 'protractor';

export class ReferralConditionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-referral-condition div table .btn-danger'));
  title = element.all(by.css('jhi-referral-condition div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
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
  reasonInput = element(by.id('field_reason'));

  questionSectionSelect = element(by.id('field_questionSection'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setAndGroupInput(andGroup: string): Promise<void> {
    await this.andGroupInput.sendKeys(andGroup);
  }

  async getAndGroupInput(): Promise<string> {
    return await this.andGroupInput.getAttribute('value');
  }

  async setTypeSelect(type: string): Promise<void> {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect(): Promise<string> {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(): Promise<void> {
    await this.typeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setQuestionIdentifierSelect(questionIdentifier: string): Promise<void> {
    await this.questionIdentifierSelect.sendKeys(questionIdentifier);
  }

  async getQuestionIdentifierSelect(): Promise<string> {
    return await this.questionIdentifierSelect.element(by.css('option:checked')).getText();
  }

  async questionIdentifierSelectLastOption(): Promise<void> {
    await this.questionIdentifierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setItemIdentifierSelect(itemIdentifier: string): Promise<void> {
    await this.itemIdentifierSelect.sendKeys(itemIdentifier);
  }

  async getItemIdentifierSelect(): Promise<string> {
    return await this.itemIdentifierSelect.element(by.css('option:checked')).getText();
  }

  async itemIdentifierSelectLastOption(): Promise<void> {
    await this.itemIdentifierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setNumberInput(number: string): Promise<void> {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput(): Promise<string> {
    return await this.numberInput.getAttribute('value');
  }

  async setReasonInput(reason: string): Promise<void> {
    await this.reasonInput.sendKeys(reason);
  }

  async getReasonInput(): Promise<string> {
    return await this.reasonInput.getAttribute('value');
  }

  async questionSectionSelectLastOption(): Promise<void> {
    await this.questionSectionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async questionSectionSelectOption(option: string): Promise<void> {
    await this.questionSectionSelect.sendKeys(option);
  }

  getQuestionSectionSelect(): ElementFinder {
    return this.questionSectionSelect;
  }

  async getQuestionSectionSelectedOption(): Promise<string> {
    return await this.questionSectionSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ReferralConditionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-referralCondition-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-referralCondition'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
