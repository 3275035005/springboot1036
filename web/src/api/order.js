import request from '@/utils/request'

export function pageQuery(page, limit, data) {
  return request({
    url: `/order/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}
export function deleteById(id) {
  return request({
    url: `/order/deleteById/${id}`,
    method: 'delete'
  })
}
export function startBuilding(data){
  return request({
    url: `/order/startBuilding`,
    method: 'put',
    data: data
  })
}
export function sendOrder(id){
  return request({
    url: `/order/sendOrder/`+id,
    method: 'post'
  })
}
export function getZzt(type){
  return request({
    url: `/order/getZzt/`+type,
    method: 'get'
  })
}
