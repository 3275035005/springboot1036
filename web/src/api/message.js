import request from '@/utils/request'

export function pageQuery(page, limit, data) {
  return request({
    url: `/message/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}
export function deleteById(id) {
  return request({
    url: `/message/deleteById/${id}`,
    method: 'delete'
  })
}

export function update(data){
  return request({
    url: `/message/update`,
    method: 'put',
    data: data
  })
}
