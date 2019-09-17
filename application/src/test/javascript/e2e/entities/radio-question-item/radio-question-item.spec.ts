/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RadioQuestionItemComponentsPage,
  RadioQuestionItemDeleteDialog,
  RadioQuestionItemUpdatePage
} from './radio-question-item.page-object';

const expect = chai.expect;

describe('RadioQuestionItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let radioQuestionItemUpdatePage: RadioQuestionItemUpdatePage;
  let radioQuestionItemComponentsPage: RadioQuestionItemComponentsPage;
  let radioQuestionItemDeleteDialog: RadioQuestionItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RadioQuestionItems', async () => {
    await navBarPage.goToEntity('radio-question-item');
    radioQuestionItemComponentsPage = new RadioQuestionItemComponentsPage();
    await browser.wait(ec.visibilityOf(radioQuestionItemComponentsPage.title), 5000);
    expect(await radioQuestionItemComponentsPage.getTitle()).to.eq('Radio Question Items');
  });

  it('should load create RadioQuestionItem page', async () => {
    await radioQuestionItemComponentsPage.clickOnCreateButton();
    radioQuestionItemUpdatePage = new RadioQuestionItemUpdatePage();
    expect(await radioQuestionItemUpdatePage.getPageTitle()).to.eq('Create or edit a Radio Question Item');
    await radioQuestionItemUpdatePage.cancel();
  });

  it('should create and save RadioQuestionItems', async () => {
    const nbButtonsBeforeCreate = await radioQuestionItemComponentsPage.countDeleteButtons();

    await radioQuestionItemComponentsPage.clickOnCreateButton();
    await promise.all([
      radioQuestionItemUpdatePage.setUuidInput('uuid'),
      radioQuestionItemUpdatePage.setLabelInput('label'),
      radioQuestionItemUpdatePage.setDescriptorInput('descriptor'),
      radioQuestionItemUpdatePage.radioQuestionSelectLastOption()
    ]);
    expect(await radioQuestionItemUpdatePage.getUuidInput()).to.eq('uuid', 'Expected Uuid value to be equals to uuid');
    expect(await radioQuestionItemUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
    expect(await radioQuestionItemUpdatePage.getDescriptorInput()).to.eq(
      'descriptor',
      'Expected Descriptor value to be equals to descriptor'
    );
    await radioQuestionItemUpdatePage.save();
    expect(await radioQuestionItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await radioQuestionItemComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last RadioQuestionItem', async () => {
    const nbButtonsBeforeDelete = await radioQuestionItemComponentsPage.countDeleteButtons();
    await radioQuestionItemComponentsPage.clickOnLastDeleteButton();

    radioQuestionItemDeleteDialog = new RadioQuestionItemDeleteDialog();
    expect(await radioQuestionItemDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Radio Question Item?');
    await radioQuestionItemDeleteDialog.clickOnConfirmButton();

    expect(await radioQuestionItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
