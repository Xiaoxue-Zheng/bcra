/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CsvFileComponentsPage, CsvFileDeleteDialog, CsvFileUpdatePage } from './csv-file.page-object';

const expect = chai.expect;

describe('CsvFile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let csvFileUpdatePage: CsvFileUpdatePage;
  let csvFileComponentsPage: CsvFileComponentsPage;
  let csvFileDeleteDialog: CsvFileDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CsvFiles', async () => {
    await navBarPage.goToEntity('csv-file');
    csvFileComponentsPage = new CsvFileComponentsPage();
    await browser.wait(ec.visibilityOf(csvFileComponentsPage.title), 5000);
    expect(await csvFileComponentsPage.getTitle()).to.eq('Csv Files');
  });

  it('should load create CsvFile page', async () => {
    await csvFileComponentsPage.clickOnCreateButton();
    csvFileUpdatePage = new CsvFileUpdatePage();
    expect(await csvFileUpdatePage.getPageTitle()).to.eq('Create or edit a Csv File');
    await csvFileUpdatePage.cancel();
  });

  it('should create and save CsvFiles', async () => {
    const nbButtonsBeforeCreate = await csvFileComponentsPage.countDeleteButtons();

    await csvFileComponentsPage.clickOnCreateButton();
    await promise.all([
      csvFileUpdatePage.stateSelectLastOption(),
      csvFileUpdatePage.setStatusInput('status'),
      csvFileUpdatePage.setFileNameInput('fileName'),
      csvFileUpdatePage.setUploadDatetimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      csvFileUpdatePage.contentSelectLastOption()
    ]);
    expect(await csvFileUpdatePage.getStatusInput()).to.eq('status', 'Expected Status value to be equals to status');
    expect(await csvFileUpdatePage.getFileNameInput()).to.eq('fileName', 'Expected FileName value to be equals to fileName');
    expect(await csvFileUpdatePage.getUploadDatetimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected uploadDatetime value to be equals to 2000-12-31'
    );
    await csvFileUpdatePage.save();
    expect(await csvFileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await csvFileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last CsvFile', async () => {
    const nbButtonsBeforeDelete = await csvFileComponentsPage.countDeleteButtons();
    await csvFileComponentsPage.clickOnLastDeleteButton();

    csvFileDeleteDialog = new CsvFileDeleteDialog();
    expect(await csvFileDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Csv File?');
    await csvFileDeleteDialog.clickOnConfirmButton();

    expect(await csvFileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
