import request from '@/utils/request'

export function listEquipment() {
  return request({
    url: '/equipment/list',
    method: 'get'
  })
}

export function getEquipment(equipmentName) {
  return request({
    url: '/equipment/' + equipmentName,
    method: 'get'
  })
}

export function getAllCurrencies() {
  return request({
    url: '/equipment/currency/list',
    method: 'get'
  })
}