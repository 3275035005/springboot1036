import request from '@/utils/request'

export function pageQuery(page, limit, data) {
  return request({
    url: `/materials/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}
export function deleteById(id) {
  return request({
    url: `/materials/deleteById/${id}`,
    method: 'delete'
  })
}
export function insert(data){
  return request({
    url: `/materials/insert`,
    method: 'post',
    data: data
  })
}

export function update(data){
  return request({
    url: `/materials/update`,
    method: 'put',
    data: data
  })
}
export function getMaterialsAll() {
  return request({
    url: '/materials/getMaterialsAll',
    method: 'get'
  })
}
