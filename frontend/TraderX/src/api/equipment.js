import request from '@/utils/request'

export function listEquipment(equipmentType) {
  return request({
    url: '/equipment/' + equipmentType + '/list',
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

export function getAllCryptoCurrencies() {
  return request({
    url: '/equipment/crypto-currency/list',
    method: 'get'
  })
}

export function getAllStocks() {
  return request({
    url: '/equipment/stock/list',
    method: 'get'
  })
}

export function depositMoney(amount) {
  return request({
    url: '/investment/deposit',
    method: 'post',
    params: amount,
  })
}

export function buyEquipment(query) {
  return request({
    url: '/transaction/buy',
    method: 'post',
    params: query,
  })
}

export function sellEquipment(query) {
  return request({
    url: '/transaction/sell',
    method: 'post',
    params: query,
  })
}

export function getAssetInfo() {
  return request({
    url: '/asset/all',
    method: 'get',
  })
}

export function createPortfolio(query) {
  return request({
    url: '/portfolio/create',
    method: 'post',
    params: query,
  })
}

export function deletePortfolio(query) {
  return request({
    url: '/portfolio/delete',
    method: 'post',
    params: query,
  })
}

export function addEquipmentToPortfolio(query) {
  return request({
    url: '/portfolio/add',
    method: 'post',
    params: query,
  })
}

export function deleteEquipmentFromPortfolio(query) {
  return request({
    url: '/portfolio/delete',
    method: 'post',
    params: query,
  })
}

export function setAlert(data) {
  return request({
    url: '/alert/set',
    method: 'post',
    data
  })
}

export function getAlert() {
  return request({
    url: '/alert/get',
    method: 'get',
  })
}

export function editAlert(data) {
  return request({
    url: '/alert/edit',
    method: 'post',
    data
  })
}

export function deleteAlert(query) {
  return request({
    url: '/alert/remove',
    method: 'delete',
    params: query
  })
}