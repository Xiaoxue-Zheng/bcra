/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DisplayConditionComponentsPage, DisplayConditionDeleteDialog, DisplayConditionUpdatePage } from './display-condition.page-object';

const expect = chai.expect;

describe('DisplayCondition e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let displayConditionUpdatePage: DisplayConditionUpdatePage;
  let displayConditionComponentsPage: DisplayConditionComponentsPage;
  let displayConditionDeleteDialog: DisplayConditionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DisplayConditions', async () => {
    await navBarPage.goToEntity('display-condition');
    displayConditionComponentsPage = new DisplayConditionComponentsPage();
    await browser.wait(ec.visibilityOf(displayConditionComponentsPage.title), 5000);
    expect(await displayConditionComponentsPage.getTitle()).to.eq('Display Conditions');
  });

  it('should load create DisplayCondition page', async () => {
    await displayConditionComponentsPage.clickOnCreateButton();
    displayConditionUpdatePage = new DisplayConditionUpdatePage();
    expect(await displayConditionUpdatePage.getPageTitle()).to.eq('Create or edit a Display Condition');
    await displayConditionUpdatePage.cancel();
  });

  it('should create and save DisplayConditions', async () => {
    const nbButtonsBeforeCreate = await displayConditionComponentsPage.countDeleteButtons();

    await displayConditionComponentsPage.clickOnCreateButton();
    await promise.all([displayConditionUpdatePage.questionGroupSelectLastOption()]);
    await displayConditionUpdatePage.save();
    expect(await displayConditionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await displayConditionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DisplayCondition', async () => {
    const nbButtonsBeforeDelete = await displayConditionComponentsPage.countDeleteButtons();
    await displayConditionComponentsPage.clickOnLastDeleteButton();

    displayConditionDeleteDialog = new DisplayConditionDeleteDialog();
    expect(await displayConditionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Display Condition?');
    await displayConditionDeleteDialog.clickOnConfirmButton();

    expect(await displayConditionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
