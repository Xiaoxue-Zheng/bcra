/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CheckboxQuestionItemComponentsPage,
  CheckboxQuestionItemDeleteDialog,
  CheckboxQuestionItemUpdatePage
} from './checkbox-question-item.page-object';

const expect = chai.expect;

describe('CheckboxQuestionItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let checkboxQuestionItemUpdatePage: CheckboxQuestionItemUpdatePage;
  let checkboxQuestionItemComponentsPage: CheckboxQuestionItemComponentsPage;
  let checkboxQuestionItemDeleteDialog: CheckboxQuestionItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CheckboxQuestionItems', async () => {
    await navBarPage.goToEntity('checkbox-question-item');
    checkboxQuestionItemComponentsPage = new CheckboxQuestionItemComponentsPage();
    await browser.wait(ec.visibilityOf(checkboxQuestionItemComponentsPage.title), 5000);
    expect(await checkboxQuestionItemComponentsPage.getTitle()).to.eq('Checkbox Question Items');
  });

  it('should load create CheckboxQuestionItem page', async () => {
    await checkboxQuestionItemComponentsPage.clickOnCreateButton();
    checkboxQuestionItemUpdatePage = new CheckboxQuestionItemUpdatePage();
    expect(await checkboxQuestionItemUpdatePage.getPageTitle()).to.eq('Create or edit a Checkbox Question Item');
    await checkboxQuestionItemUpdatePage.cancel();
  });

  it('should create and save CheckboxQuestionItems', async () => {
    const nbButtonsBeforeCreate = await checkboxQuestionItemComponentsPage.countDeleteButtons();

    await checkboxQuestionItemComponentsPage.clickOnCreateButton();
    await promise.all([
      checkboxQuestionItemUpdatePage.setUuidInput('uuid'),
      checkboxQuestionItemUpdatePage.setLabelInput('label'),
      checkboxQuestionItemUpdatePage.setDescriptorInput('descriptor'),
      checkboxQuestionItemUpdatePage.checkboxQuestionSelectLastOption()
    ]);
    expect(await checkboxQuestionItemUpdatePage.getUuidInput()).to.eq('uuid', 'Expected Uuid value to be equals to uuid');
    expect(await checkboxQuestionItemUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
    expect(await checkboxQuestionItemUpdatePage.getDescriptorInput()).to.eq(
      'descriptor',
      'Expected Descriptor value to be equals to descriptor'
    );
    await checkboxQuestionItemUpdatePage.save();
    expect(await checkboxQuestionItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await checkboxQuestionItemComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CheckboxQuestionItem', async () => {
    const nbButtonsBeforeDelete = await checkboxQuestionItemComponentsPage.countDeleteButtons();
    await checkboxQuestionItemComponentsPage.clickOnLastDeleteButton();

    checkboxQuestionItemDeleteDialog = new CheckboxQuestionItemDeleteDialog();
    expect(await checkboxQuestionItemDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Checkbox Question Item?');
    await checkboxQuestionItemDeleteDialog.clickOnConfirmButton();

    expect(await checkboxQuestionItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
