import { SecurityService } from '@/api/security.service'
import ApiService from '@/api/api.service'

describe('SecurityService', () => {
    let service = null

    async function postFormDataSuccess() {
        return new Promise(resolve => {
            resolve("SUCCESS")
        })
    }

    async function postFormDataNoResponse() {
        return new Promise((resolve, reject) => {
            let error = {}
            reject(error)
        })
    }

    async function postFormDataUnauthorized() {
        return new Promise((resolve, reject) => {
            let error = { response: { status: 401 } }
            reject(error)
        })
    }

    async function postFormDataGenericError() {
        return new Promise((resolve, reject) => {
            let error = { response : { status: 999 } }
            reject(error)
        })
    }

    beforeEach(() => {
        service = SecurityService
    })

    describe('login', () => {
        it('should return "SUCCESS" on a successful login', async (done) => {
            ApiService.postFormData = postFormDataSuccess
            service.login('test', 'test1234').then((result) => {
                expect(result).toBe("SUCCESS")
                done()
            })
        })

        it('should return "ERROR" when no response is recieved', async (done) => {
            ApiService.postFormData = postFormDataNoResponse
            service.login('test', 'test1234').then((result) => {
                expect(result).toBe("ERROR")
                done()
            })
        })

        it('should return "UNAUTHORIZED" on an unsuccessful login', async (done) => {
            ApiService.postFormData = postFormDataUnauthorized
            service.login('test', 'test1234').then((result) => {
                let res = {"status": 401}
                expect(result).toEqual(res)
                done()
            })
        })

        it('should return "ERROR" in all other cases', async (done) => {
            ApiService.postFormData = postFormDataGenericError
            service.login('test', 'test1234').then((result) => {
                expect(result).toBe("ERROR")
                done()
            })
        })
    })
})
