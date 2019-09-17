/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CheckboxDisplayConditionComponentsPage,
  CheckboxDisplayConditionDeleteDialog,
  CheckboxDisplayConditionUpdatePage
} from './checkbox-display-condition.page-object';

const expect = chai.expect;

describe('CheckboxDisplayCondition e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let checkboxDisplayConditionUpdatePage: CheckboxDisplayConditionUpdatePage;
  let checkboxDisplayConditionComponentsPage: CheckboxDisplayConditionComponentsPage;
  let checkboxDisplayConditionDeleteDialog: CheckboxDisplayConditionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CheckboxDisplayConditions', async () => {
    await navBarPage.goToEntity('checkbox-display-condition');
    checkboxDisplayConditionComponentsPage = new CheckboxDisplayConditionComponentsPage();
    await browser.wait(ec.visibilityOf(checkboxDisplayConditionComponentsPage.title), 5000);
    expect(await checkboxDisplayConditionComponentsPage.getTitle()).to.eq('Checkbox Display Conditions');
  });

  it('should load create CheckboxDisplayCondition page', async () => {
    await checkboxDisplayConditionComponentsPage.clickOnCreateButton();
    checkboxDisplayConditionUpdatePage = new CheckboxDisplayConditionUpdatePage();
    expect(await checkboxDisplayConditionUpdatePage.getPageTitle()).to.eq('Create or edit a Checkbox Display Condition');
    await checkboxDisplayConditionUpdatePage.cancel();
  });

  it('should create and save CheckboxDisplayConditions', async () => {
    const nbButtonsBeforeCreate = await checkboxDisplayConditionComponentsPage.countDeleteButtons();

    await checkboxDisplayConditionComponentsPage.clickOnCreateButton();
    await promise.all([checkboxDisplayConditionUpdatePage.checkboxQuestionItemSelectLastOption()]);
    await checkboxDisplayConditionUpdatePage.save();
    expect(await checkboxDisplayConditionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await checkboxDisplayConditionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CheckboxDisplayCondition', async () => {
    const nbButtonsBeforeDelete = await checkboxDisplayConditionComponentsPage.countDeleteButtons();
    await checkboxDisplayConditionComponentsPage.clickOnLastDeleteButton();

    checkboxDisplayConditionDeleteDialog = new CheckboxDisplayConditionDeleteDialog();
    expect(await checkboxDisplayConditionDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Checkbox Display Condition?'
    );
    await checkboxDisplayConditionDeleteDialog.clickOnConfirmButton();

    expect(await checkboxDisplayConditionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
