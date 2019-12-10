/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CsvContentComponentsPage, CsvContentDeleteDialog, CsvContentUpdatePage } from './csv-content.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('CsvContent e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let csvContentUpdatePage: CsvContentUpdatePage;
  let csvContentComponentsPage: CsvContentComponentsPage;
  /*let csvContentDeleteDialog: CsvContentDeleteDialog;*/
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CsvContents', async () => {
    await navBarPage.goToEntity('csv-content');
    csvContentComponentsPage = new CsvContentComponentsPage();
    await browser.wait(ec.visibilityOf(csvContentComponentsPage.title), 5000);
    expect(await csvContentComponentsPage.getTitle()).to.eq('Csv Contents');
  });

  it('should load create CsvContent page', async () => {
    await csvContentComponentsPage.clickOnCreateButton();
    csvContentUpdatePage = new CsvContentUpdatePage();
    expect(await csvContentUpdatePage.getPageTitle()).to.eq('Create or edit a Csv Content');
    await csvContentUpdatePage.cancel();
  });

  /* it('should create and save CsvContents', async () => {
        const nbButtonsBeforeCreate = await csvContentComponentsPage.countDeleteButtons();

        await csvContentComponentsPage.clickOnCreateButton();
        await promise.all([
            csvContentUpdatePage.setDataInput(absolutePath),
        ]);
        expect(await csvContentUpdatePage.getDataInput()).to.endsWith(fileNameToUpload, 'Expected Data value to be end with ' + fileNameToUpload);
        await csvContentUpdatePage.save();
        expect(await csvContentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await csvContentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last CsvContent', async () => {
        const nbButtonsBeforeDelete = await csvContentComponentsPage.countDeleteButtons();
        await csvContentComponentsPage.clickOnLastDeleteButton();

        csvContentDeleteDialog = new CsvContentDeleteDialog();
        expect(await csvContentDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Csv Content?');
        await csvContentDeleteDialog.clickOnConfirmButton();

        expect(await csvContentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
