/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CheckboxAnswerItemComponentsPage,
  CheckboxAnswerItemDeleteDialog,
  CheckboxAnswerItemUpdatePage
} from './checkbox-answer-item.page-object';

const expect = chai.expect;

describe('CheckboxAnswerItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let checkboxAnswerItemUpdatePage: CheckboxAnswerItemUpdatePage;
  let checkboxAnswerItemComponentsPage: CheckboxAnswerItemComponentsPage;
  let checkboxAnswerItemDeleteDialog: CheckboxAnswerItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CheckboxAnswerItems', async () => {
    await navBarPage.goToEntity('checkbox-answer-item');
    checkboxAnswerItemComponentsPage = new CheckboxAnswerItemComponentsPage();
    await browser.wait(ec.visibilityOf(checkboxAnswerItemComponentsPage.title), 5000);
    expect(await checkboxAnswerItemComponentsPage.getTitle()).to.eq('Checkbox Answer Items');
  });

  it('should load create CheckboxAnswerItem page', async () => {
    await checkboxAnswerItemComponentsPage.clickOnCreateButton();
    checkboxAnswerItemUpdatePage = new CheckboxAnswerItemUpdatePage();
    expect(await checkboxAnswerItemUpdatePage.getPageTitle()).to.eq('Create or edit a Checkbox Answer Item');
    await checkboxAnswerItemUpdatePage.cancel();
  });

  it('should create and save CheckboxAnswerItems', async () => {
    const nbButtonsBeforeCreate = await checkboxAnswerItemComponentsPage.countDeleteButtons();

    await checkboxAnswerItemComponentsPage.clickOnCreateButton();
    await promise.all([
      checkboxAnswerItemUpdatePage.checkboxAnswerSelectLastOption(),
      checkboxAnswerItemUpdatePage.checkboxQuestionItemSelectLastOption()
    ]);
    await checkboxAnswerItemUpdatePage.save();
    expect(await checkboxAnswerItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await checkboxAnswerItemComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CheckboxAnswerItem', async () => {
    const nbButtonsBeforeDelete = await checkboxAnswerItemComponentsPage.countDeleteButtons();
    await checkboxAnswerItemComponentsPage.clickOnLastDeleteButton();

    checkboxAnswerItemDeleteDialog = new CheckboxAnswerItemDeleteDialog();
    expect(await checkboxAnswerItemDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Checkbox Answer Item?');
    await checkboxAnswerItemDeleteDialog.clickOnConfirmButton();

    expect(await checkboxAnswerItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
