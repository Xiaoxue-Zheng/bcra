import { StorageService } from '@/services/storage.service'

describe('StorageService', () => {
    let service = null

    beforeEach(() => {
        service = StorageService
        service.clear()
    })

    describe('get and set', () => {
        it('should store and return a value from memory', () => {
            let key = "KEY"
            let val = "VALUE"
            service.set(key, val)
            let result = service.get(key)
            expect(result).toEqual(val)
        })

        it('should return null if no matching key can be found', () => {
            let result = service.get("NON_EXISTANT_KEY")
            expect(result).toEqual(null)
        })
    })

    describe('remove', () => {
        it('should remove only one specific key value pair from memory', () => {
            let key1 = "KEY1"
            let val1 = "VAL1"
            let key2 = "KEY2"
            let val2 = "VAL2"

            service.set(key1, val1)
            service.set(key2, val2)

            service.remove(key1)

            let key1Result = service.get(key1)
            let key2Result = service.get(key2)

            expect(key1Result).toEqual(null)
            expect(key2Result).toEqual(val2)
        })
    })

    describe('clear', () => {
        it('should clear all key value pairs from memory', () => {
            let key1 = "KEY1"
            let val1 = "VAL1"
            let key2 = "KEY2"
            let val2 = "VAL2"

            service.set(key1, val1)
            service.set(key2, val2)

            service.clear(key1)

            let key1Result = service.get(key1)
            let key2Result = service.get(key2)

            expect(key1Result).toEqual(null)
            expect(key2Result).toEqual(null)
        })
    })
})
