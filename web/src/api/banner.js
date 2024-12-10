import request from '@/utils/request'

export function pageQuery(page, limit, data) {
  return request({
    url: `/banner/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}
export function deleteById(id) {
  return request({
    url: `/banner/deleteById/${id}`,
    method: 'delete'
  })
}
export function insert(data){
  return request({
    url: `/banner/insert`,
    method: 'post',
    data: data
  })
}

export function update(data){
  return request({
    url: `/banner/update`,
    method: 'put',
    data: data
  })
}
