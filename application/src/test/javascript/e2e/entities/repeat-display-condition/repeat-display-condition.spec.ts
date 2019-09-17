/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RepeatDisplayConditionComponentsPage,
  RepeatDisplayConditionDeleteDialog,
  RepeatDisplayConditionUpdatePage
} from './repeat-display-condition.page-object';

const expect = chai.expect;

describe('RepeatDisplayCondition e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let repeatDisplayConditionUpdatePage: RepeatDisplayConditionUpdatePage;
  let repeatDisplayConditionComponentsPage: RepeatDisplayConditionComponentsPage;
  let repeatDisplayConditionDeleteDialog: RepeatDisplayConditionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RepeatDisplayConditions', async () => {
    await navBarPage.goToEntity('repeat-display-condition');
    repeatDisplayConditionComponentsPage = new RepeatDisplayConditionComponentsPage();
    await browser.wait(ec.visibilityOf(repeatDisplayConditionComponentsPage.title), 5000);
    expect(await repeatDisplayConditionComponentsPage.getTitle()).to.eq('Repeat Display Conditions');
  });

  it('should load create RepeatDisplayCondition page', async () => {
    await repeatDisplayConditionComponentsPage.clickOnCreateButton();
    repeatDisplayConditionUpdatePage = new RepeatDisplayConditionUpdatePage();
    expect(await repeatDisplayConditionUpdatePage.getPageTitle()).to.eq('Create or edit a Repeat Display Condition');
    await repeatDisplayConditionUpdatePage.cancel();
  });

  it('should create and save RepeatDisplayConditions', async () => {
    const nbButtonsBeforeCreate = await repeatDisplayConditionComponentsPage.countDeleteButtons();

    await repeatDisplayConditionComponentsPage.clickOnCreateButton();
    await promise.all([repeatDisplayConditionUpdatePage.repeatQuestionSelectLastOption()]);
    await repeatDisplayConditionUpdatePage.save();
    expect(await repeatDisplayConditionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await repeatDisplayConditionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last RepeatDisplayCondition', async () => {
    const nbButtonsBeforeDelete = await repeatDisplayConditionComponentsPage.countDeleteButtons();
    await repeatDisplayConditionComponentsPage.clickOnLastDeleteButton();

    repeatDisplayConditionDeleteDialog = new RepeatDisplayConditionDeleteDialog();
    expect(await repeatDisplayConditionDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Repeat Display Condition?'
    );
    await repeatDisplayConditionDeleteDialog.clickOnConfirmButton();

    expect(await repeatDisplayConditionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
