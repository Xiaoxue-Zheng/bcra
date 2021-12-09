<template>
    <footer class="footer">
        <div class="pure-g">
            <div class="pure-u-1 pure-u-md-1-4 footer-left">
                <a class="pure-menu-link footerLink" href="https://mft.nhs.uk/privacy-policy/" target="_blank">Privacy Policy - MFT</a>
                <a class="pure-menu-link footerLink" href="https://research.cmft.nhs.uk/getting-involved/gdpr-and-research" target="_blank">Research Privacy Information - MFT</a>
                <router-link class="pure-menu-link footerLink" to="/accessibility">Accessibility Statement</router-link>
            </div>
            <div class="pure-u-1 pure-u-md-1-4 footer-middle">
                <img alt="Manchester Cancer Research Centre" src="../assets/mcrc-logo.png" height="100px">
            </div>
            <div class="pure-u-1 pure-u-md-1-4 footer-middle">
                <img alt="The University of Manchester" src="../assets/uom-logo.png" height="100px">
            </div>

            <div class="pure-u-1 pure-u-md-1-4 footer-right">
                <img alt="Shine Bright Foundation" src="../assets/sbf_logo_lips.jpg" height="100px">
            </div>
        </div>
    </footer>
</template>
<style scoped>
    .footerLink {
        padding: 0 !important;
    }
</style>
